package com.board.controller.moderator;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ModeratorLogOutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorLogOutServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("userId");
        session.removeAttribute("email");
        session.removeAttribute("password");
        session.removeAttribute("roles");
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/");
        LOGGER.info("LogOut by Moderator");
    }
}
