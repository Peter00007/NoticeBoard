package com.board.controller.moderator;

import com.board.controller.admin.AdminAccountServlet;
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

public class ModeratorAccountServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorAccountServlet.class);
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
                response.sendRedirect(request.getContextPath() + "/moderator");
                LOGGER.info("Updated Moderator account");
            } else {
                LOGGER.info("Incorrect data for updating Moderator account");
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for updating Moderator account");
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
        return f;
    }
}
