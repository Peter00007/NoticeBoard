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

public class ModeratorUpdateTypeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorUpdateTypeServlet.class);

    private TypeService typeService = new TypeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Type typeForEdit = typeService.getById(Integer.parseInt((String) session.getAttribute("typeId")));
        request.setAttribute("type", typeForEdit);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/updateType.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("typeId");
        String type = request.getParameter("type");
        HttpSession session = request.getSession();

        boolean error = getError(type, request);
        if (!id.equals("") && !type.equals("")) {
            if (error) {
                Type newType = new Type();
                newType.setId(Integer.parseInt(id));
                newType.setType(type);

                typeService.update(newType);
                session.removeAttribute("typeId");
                session.setAttribute("updated", "You have successfully updated type");
                response.sendRedirect(request.getContextPath() + "/moderator/manageTypes");
                LOGGER.info("Updated Type");
            } else {
             //   RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/updateType.jsp");
             //   requestDispatcher.forward(request, response);
                LOGGER.info("Incorrect data for Updating Type");
                doGet(request, response);
            }
        } else {
          //  RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/updateType.jsp");
          //  requestDispatcher.forward(request, response);
            LOGGER.info("Incorrect data for Updating Type");
            doGet(request, response);
        }
    }

    private boolean getError(String type, HttpServletRequest request) {
        boolean f = true;
        if (type.length() < 3 || type.length() > 40) {
            request.setAttribute("error","Type must be contains from 3 to 40 symbols.");
            f = false;
        }
        return f;
    }
}
