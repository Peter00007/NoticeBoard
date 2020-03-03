package com.board.controller;

import com.board.model.Role;
import com.board.model.User;
import com.board.service.RoleService;
import com.board.service.UserService;
import com.board.service.impl.RoleServiceImpl;
import com.board.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationServlet extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(AuthorizationServlet.class);
    private UserService userService = new UserServiceImpl();
    private RoleService roleService = new RoleServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<String> roles = (List<String>) session.getAttribute("roles");

        if (roles == null) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/authorization.jsp");
            requestDispatcher.forward(request, response);
        } else {
            String role = roles.get(0);
            switch (role) {
                case "User":
                    response.sendRedirect(request.getContextPath() + "/user");
                    break;
                case "Moderator":
                    response.sendRedirect(request.getContextPath() + "/moderator");
                    break;
                case "Admin":
                    response.sendRedirect(request.getContextPath() + "/admin");
                    break;
            }
        }
        session.removeAttribute("error");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String button = request.getParameter("button");

        switch (button) {
            case "login":
                login(request, response);
                break;
            case "forgotPassword":
                response.sendRedirect(request.getContextPath() + "/recoveryPassword");
                break;
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        boolean error = getError(email, password, request);
        if (!email.equals("") && !password.equals("")) {
            User user = userService.getUserForAutorization(email, password);
            if (error) {
                if (user != null) {
                    List<Role> roles = roleService.getAllRoleByUser(user.getId());
                    if (!roles.isEmpty()) {
                        List<String> roleList = new ArrayList<>();
                        for (Role role : roles) {
                            roleList.add(role.getRole());
                        }
                        String role = roleList.get(0);

                        session.setAttribute("userId", user.getId());
                        session.setAttribute("email", email);
                        session.setAttribute("password", password);
                        session.setAttribute("roles", roleList);
                        if (role != null && roleList.size() > 1) {
                            response.sendRedirect(request.getContextPath() + "/choiceRole");
                        } else {
                            LOGGER.info("Authorized User");
                            switch (role) {
                                case "User":
                                    response.sendRedirect(request.getContextPath() + "/user");
                                    break;
                                case "Moderator":
                                    response.sendRedirect(request.getContextPath() + "/moderator");
                                    break;
                                case "Admin":
                                    response.sendRedirect(request.getContextPath() + "/admin");
                                    break;
                            }
                        }
                    } else {
                        LOGGER.info("Incorrect data for Authorization. User don't have any role");
                        request.setAttribute("error", "User with this data not registered here.");
                        doGet(request, response);
                    }
                } else {
                    LOGGER.info("Incorrect data for Authorization");
                    request.setAttribute("error", "User with this data not registered here.");
                    doGet(request, response);
                }
            } else {
                LOGGER.info("Incorrect data for Authorization");
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for Authorization");
            doGet(request, response);
        }
    }

    private boolean getError(String email, String password, HttpServletRequest request) {
        boolean f = true;
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
