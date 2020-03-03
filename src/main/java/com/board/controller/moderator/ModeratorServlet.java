package com.board.controller.moderator;

import com.board.model.Notice;
import com.board.model.NoticeStatus;
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

public class ModeratorServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorServlet.class);
    private NoticeService noticeService = new NoticeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Notice> notices = noticeService.getAllNotice(String.valueOf(NoticeStatus.APPROVED));
        request.setAttribute("notices", notices);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("moderator/moderator.jsp");
        requestDispatcher.forward(request, response);
        session.removeAttribute("error");
        session.removeAttribute("message");
        LOGGER.info("Getting main Moderator page");
    }
}
