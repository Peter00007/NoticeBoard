package com.board.controller.admin;

import com.board.model.Role;
import com.board.model.User;
import com.board.model.UserStatus;
import com.board.service.RoleService;
import com.board.service.UserService;
import com.board.service.impl.RoleServiceImpl;
import com.board.service.impl.UserServiceImpl;
import com.board.utils.GenerateUserPasswordUtil;
import com.board.utils.MailerUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AdminAddUserServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AdminAddUserServlet.class);
    private UserService userService = new UserServiceImpl();
    private RoleService roleService = new RoleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Role> roles = roleService.getAllRoles();
        request.setAttribute("roles", roles);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/admin/addUser.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String[] roles = request.getParameterValues("roleId");

        boolean error = getError(name, lastName, email, roles, request);
        if (!name.equals("") && !lastName.equals("") && !email.equals("") && roles != null) {
            if (error) {
                String password = GenerateUserPasswordUtil.generatePassword();
                User userToSave = new User();
                userToSave.setName(name);
                userToSave.setLastName(lastName);
                userToSave.setPassword(password);
                userToSave.setEmail(email);
                userToSave.setUserStatus(UserStatus.CREATED);

                User user = userService.create(userToSave);
                for (String role : roles) {
                    roleService.insertIntoUserRoles(user.getId(), Integer.parseInt(role));
                }
                HttpSession session = request.getSession();
                String userData = "Hello! You registered in our site. Welcome http://localhost:8080/authorization\n"
                        + "This is your credentials:[ " + password + " ]  " + user.getEmail();
                MailerUtil.send(user.getEmail(), "Registration", userData);
                LOGGER.info("Added User by Admin");
                session.setAttribute("created", "You have successfully created user");
                response.sendRedirect(request.getContextPath() + "/admin/manageUsers");
            } else {
                LOGGER.info("Incorrect data in Adding new User by Admin");
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data in Adding new User by Admin");
            doGet(request, response);
        }
    }

    private boolean getError(String firstName, String lastName, String email, String[] roles, HttpServletRequest request) {
        boolean f = true;
        if (!firstName.matches("[A-Z][a-z]{1,19}")) {
            request.setAttribute("firstName", "Name must start from Upper letter and contains from 2 to 20 letters and without any else symbols.");
            f = false;
        }
        if (!lastName.matches("[A-Z][a-z]{1,19}")) {
            request.setAttribute("lastName", "Last Name must start from Upper letter and contains from 2 to 20 letters and without any else symbols.");
            f = false;
        }
        if (!email.matches("([a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\.(arpa|root|aero|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)|([0-9]{1,3}\\.{3}[0-9]{1,3}))")) {
            request.setAttribute("email", "Invalid email.");
            f = false;
        }
        if (roles == null) {
            request.setAttribute("rolesForEdit", "Choose at least role.");
            f = false;
        }
        return f;
    }
}
