package com.board.controller;

import com.board.dao.RoleDao;
import com.board.dao.impl.RoleDaoImpl;
import com.board.model.User;
import com.board.model.UserStatus;
import com.board.service.UserService;
import com.board.service.impl.UserServiceImpl;
import com.board.utils.ActivationCodeUtil;
import com.board.utils.MailerUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class RegistrationServlet extends HttpServlet {
    private UserService userService = new UserServiceImpl();
    private RoleDao roleDao = new RoleDaoImpl();
    private final Logger LOGGER = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/registration.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        HttpSession session = request.getSession();

        boolean error = getError(name, lastName, password, confirmPassword, email, request);
        if (!name.equals("") && !lastName.equals("") && !password.equals("") && !email.equals("") &&
                !confirmPassword.equals("")) {
            if (error) {
                if (password.equals(confirmPassword)) {
                    User user = userService.getRegisteredOrActiveUserByEmail(email);
                    if (user.getEmail() == null) {
                        String activationCode = ActivationCodeUtil.getPassword();
                        User userToSave = new User();
                        userToSave.setName(name);
                        userToSave.setLastName(lastName);
                        userToSave.setEmail(email);
                        userToSave.setUserStatus(UserStatus.REGISTERED);
                        userToSave.setPassword(password);
                        userToSave.setConfirmActivationCode(activationCode);
                        User createUser = userService.create(userToSave);
                        roleDao.insertIntoUserRoles(createUser.getId(), 1);
                        String uri = request.getScheme() + "://localhost:" + request.getServerPort() + request.getContextPath() + "/confirmRegistration?userId=" + createUser.getId();
                        MailerUtil.send(email, "Code for Activate Your Profile and Link", uri + " , " + activationCode);
                        session.setAttribute("message", "Congratulations! Check your email " + createUser.getEmail() + " and will activate account!");
                        response.sendRedirect(request.getContextPath() + "/");
                        LOGGER.info("Succeed registration. Next step - confirm your account by check email.");
                    } else {
                        LOGGER.info("Incorrect data for registration. User with this email is already registered");
                        request.setAttribute("error", "User with this email is already registered");
                        doGet(request, response);
                    }
                } else {
                    LOGGER.info("Incorrect data for registration. Password and confirm password must be equals");
                    request.setAttribute("error", "Password and confirm password must be equals");
                    doGet(request, response);
                }
            } else {
                LOGGER.info("Incorrect data for registration");
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for registration");
            doGet(request, response);
        }
    }

    private boolean getError(String firstName, String lastName, String password, String confirmPassword, String email, HttpServletRequest request) {
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
        if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}")) {
            request.setAttribute("password", "Password must be contains at least one Upper letter, one Low letter and one number and contains from 8 to 25 symbols.");
            f = false;
        }
        if (!confirmPassword.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,25}")) {
            request.setAttribute("confirmPassword", "Confirm password must be contains at least one Upper letter, one Low letter and one number and contains from 8 to 25 symbols.");
            f = false;
        }
        return f;
    }
}
