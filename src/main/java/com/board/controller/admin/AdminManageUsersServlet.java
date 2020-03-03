package com.board.controller.admin;

import com.board.model.User;
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
import java.util.List;

public class AdminManageUsersServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AdminManageUsersServlet.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<User> users = userService.getUsersAndModerators();
        request.setAttribute("users", users);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/admin/manageUsers.jsp");
        requestDispatcher.forward(request, response);
        session.removeAttribute("updated");
        session.removeAttribute("created");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String button = request.getParameter("button");

        switch (button) {
            case "editUserForm":
                updateUserForm(request, response);
                break;
            case "addUserFrom":
                response.sendRedirect(request.getContextPath() + "/admin/manageUsers/add");
                LOGGER.info("Redirected on Add User Form");
                break;
            case "deleteUser":
                deleteUser(request, response);
                break;
        }
    }

    private void updateUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("updateUserId", request.getParameter("userId"));
        response.sendRedirect(request.getContextPath() + "/admin/manageUsers/update");
        LOGGER.info("Redirected on Update User Form");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("userId"));
        userService.delete(id);
        request.setAttribute("deleted", "You have successfully deleted user");
        doGet(request, response);
        LOGGER.info("Deleted User by Admin");
    }
}
