package com.board.controller;

import com.board.model.User;
import com.board.service.UserService;
import com.board.service.impl.UserServiceImpl;
import com.board.utils.EncodingPasswordUtil;
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

public class ForgotPasswordServlet extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(ForgotPasswordServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/forgotPassword.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("confirmEmail");
        User user = userService.getUserByEmail(email);
        HttpSession session = request.getSession();
        if (user.getEmail() != null) {
            String password = GenerateUserPasswordUtil.generatePassword();
            user.setPassword(EncodingPasswordUtil.encodingPassword(password));
            userService.update(user);
            MailerUtil.send(email, "Your new password!", password);
            session.setAttribute("message", "You receive your new password in your email " + user.getEmail());
            response.sendRedirect(request.getContextPath() + "/");
            LOGGER.info("Succeed recovery password");
        } else {
            LOGGER.info("Recovery password. The user with this email is not registered in system");
            request.setAttribute("error","The user with this email is not registered in system");
            doGet(request, response);
        }
    }

}

