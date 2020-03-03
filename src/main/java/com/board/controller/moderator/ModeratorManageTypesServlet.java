package com.board.controller.moderator;

import com.board.model.Notice;
import com.board.model.Type;
import com.board.service.NoticeService;
import com.board.service.TypeService;
import com.board.service.impl.NoticeServiceImpl;
import com.board.service.impl.TypeServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ModeratorManageTypesServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorManageTypesServlet.class);
    TypeService typeService = new TypeServiceImpl();
    NoticeService noticeService = new NoticeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Type> types = typeService.getAllType();
        request.setAttribute("types", types);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/manageTypes.jsp");
        requestDispatcher.forward(request, response);
        session.removeAttribute("updated");
        session.removeAttribute("created");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String button = request.getParameter("button");

        switch (button) {
            case "updateTypeForm":
                updateTypeForm(request, response);
                break;
            case "addTypeForm":
                response.sendRedirect(request.getContextPath() + "/moderator/manageTypes/add");
                LOGGER.info("Redirect on Add Type page");
                break;
            case "deleteType":
                deleteType(request, response);
        }
    }

    private void updateTypeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("typeId", request.getParameter("typeId"));
        response.sendRedirect(request.getContextPath() + "/moderator/manageTypes/update");
        LOGGER.info("Redirect on Update Type page");
    }

    private void deleteType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("typeId"));
        List<Notice> notices = noticeService.getAllByType(id);
        if (notices.isEmpty()) {
            typeService.delete(id);
            List<Type> types = typeService.getAllType();
            request.setAttribute("list", types);
            request.setAttribute("deleted", "You have successfully deleted type");
            doGet(request, response);
            LOGGER.info("Deleted Type by Moderator");
        } else {
            LOGGER.info("Can't delete this type before will delete all notices with this type");
            request.setAttribute("error", "You can't delete this type before you'll delete all notices with this type");
            doGet(request, response);
        }
    }
}
