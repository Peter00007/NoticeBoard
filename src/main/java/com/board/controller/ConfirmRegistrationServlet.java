package com.board.controller;

import com.board.model.User;
import com.board.model.UserStatus;
import com.board.service.UserService;
import com.board.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ConfirmRegistrationServlet extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(ConfirmRegistrationServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/confirmRegistration.jsp");
        requestDispatcher.forward(request, response);
        session.removeAttribute("error");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String confirmActivationCode = request.getParameter("activationCode");
        String userId = request.getQueryString().replace("userId=", "");
        User user = userService.getById(Integer.parseInt(userId));
        HttpSession session = request.getSession();
        if (confirmActivationCode.equals(user.getConfirmActivationCode())) {
            user.setUserStatus(UserStatus.CREATED);
            userService.update(user);
            session.setAttribute("message", "Congratulations! You have successfully activated your account");
            response.sendRedirect(request.getContextPath() + "/");
            LOGGER.info("Succeed confirm registration. Congratulations!");
        } else {
            LOGGER.info("Incorrect data for confirm registration.Wrong code.");
            session.setAttribute("error", "Wrong code. Please, check your email and try again");
            response.sendRedirect(request.getRequestURL() + "?" + request.getQueryString());
        }
    }
}
