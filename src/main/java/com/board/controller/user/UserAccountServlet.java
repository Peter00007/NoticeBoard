package com.board.controller.user;

import com.board.model.User;
import com.board.model.UserStatus;
import com.board.service.UserService;
import com.board.service.impl.UserServiceImpl;
import com.board.utils.EncodingPasswordUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserAccountServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserAccountServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("url", request.getRequestURI());
        User userForEdit = userService.getById((Integer) session.getAttribute("userId"));
        request.setAttribute("userForEdit", userForEdit);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/user/account.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idOfUpdatedUser"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        User userToSave = new User();

        boolean error = getError(firstName, lastName, password, email, request);
        if (!firstName.equals("") && !lastName.equals("") && !password.equals("") && !email.equals("")) {
            if (error) {
                userToSave.setId(id);
                userToSave.setName(firstName);
                userToSave.setLastName(lastName);
                userToSave.setPassword(EncodingPasswordUtil.encodingPassword(password));
                userToSave.setEmail(email);
                userToSave.setUserStatus(UserStatus.CREATED);
                userService.update(userToSave);
                session.setAttribute("message", "You have successfully modified your account");
                response.sendRedirect(request.getContextPath() + "/user");
                LOGGER.info("Updated User account");
            } else {
                LOGGER.info("Incorrect data for updating User account");
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for updating User account");
            doGet(request, response);
        }
    }

    private boolean getError(String firstName, String lastName, String password, String email, HttpServletRequest request) {
        boolean f = true;
        if (!firstName.matches("[A-Z][a-z]{1,19}")) {
            request.setAttribute("firstName", "Name must start from Upper letter and contains from 2 to 20 letters and without any else symbols.");
            f = false;
        }
        if (!lastName.matches("[A-Z][a-z]{1,19}")) {
            request.setAttribute("lastName", "Last Name must start from Upper letter and contains from 2 to 20 letters and without any else symbols.");
            f = false;
        }
        if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}")) {
            request.setAttribute("password", "Password must be contains at least one Upper letter, one Low letter and one number and contains from 8 to 25 symbols.");
            f = false;
        }
        if (!email.matches("([a-z0-9][-a-z0-9_\\+\\.]*[a-z0-9])@([a-z0-9][-a-z0-9\\.]*[a-z0-9]\\.(arpa|root|aero|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|ac|ad|ae|af|ag|ai|al|am|an|ao|aq|ar|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|io|iq|ir|is|it|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|mg|mh|mk|ml|mm|mn|mo|mp|mq|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|pa|pe|pf|pg|ph|pk|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ro|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|ye|yt|yu|za|zm|zw)|([0-9]{1,3}\\.{3}[0-9]{1,3}))")) {
            request.setAttribute("email", "Invalid email.");
            f = false;
        }
        return f;
    }
}
