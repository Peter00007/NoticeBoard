package com.board.controller.moderator;

import com.board.model.Type;
import com.board.service.TypeService;
import com.board.service.impl.TypeServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ModeratorAddTypeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorAddTypeServlet.class);
    private TypeService typeService = new TypeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/addType.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        HttpSession session = request.getSession();

        String error = getError(type);
        if (!type.equals("")) {
            if (error.equals("")) {
                Type newType = new Type();
                newType.setType(type);

                typeService.create(newType);
                session.setAttribute("created", "You have successfully created type");
                response.sendRedirect(request.getContextPath() + "/moderator/manageTypes");
                LOGGER.info("Added Type by Moderator");
            } else {
                LOGGER.info("Incorrect data for add Type by Moderator");
                request.setAttribute("error", error);
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for add Type by Moderator");
            request.setAttribute("error", error);
            doGet(request, response);
        }
    }

    private String getError(String type) {
        String error = "Input correct data please:\n";
        boolean f = true;

        if (type.length() < 3 || type.length() > 40) {
            error = "Type must be contains from 3 to 40 symbols.";
            f = false;
        }
        if (f) {
            error = "";
        }
        return error;
    }
}
