package com.board.controller.user;

import com.board.model.Notice;
import com.board.service.NoticeService;
import com.board.service.impl.NoticeServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserNoticesServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserNoticesServlet.class);
    private NoticeService noticeService = new NoticeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Notice> notices = noticeService.getAllNoticeByUser((Integer) session.getAttribute("userId"));
        request.setAttribute("notices", notices);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(request.getServletPath() + ".jsp");
        requestDispatcher.forward(request, response);
        session.removeAttribute("error");
        session.removeAttribute("message");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String button = request.getParameter("button");

        switch (button) {
            case "updateNoticeForm":
                updateNoticeForm(request, response);
                break;
            case "deleteNotice":
                deleteNotice(request, response);
                break;
            case "addNoticeForm":
                response.sendRedirect(request.getContextPath() + "/user/notices/add");
                LOGGER.info("Redirect on add Notice page");
                break;
        }
    }

    private void updateNoticeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("noticeId", request.getParameter("noticeId"));
        response.sendRedirect(request.getContextPath() + "/user/notices/update");
        LOGGER.info("Redirect on update Notice page");
    }

    private void deleteNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("noticeId");
        int id = Integer.parseInt(strId);
        noticeService.delete(id);
        request.setAttribute("delete", "You have successfully deleted your notice");
        doGet(request, response);
        LOGGER.info("Deleted Notice by User");
    }
}
