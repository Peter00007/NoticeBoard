package com.board.controller;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChoiceRoleServlet extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(ChoiceRoleServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/view/chooseRolePanel.jsp");
        requestDispatcher.forward(request, response);
        LOGGER.info("Choice Role for continue work");
    }
}
