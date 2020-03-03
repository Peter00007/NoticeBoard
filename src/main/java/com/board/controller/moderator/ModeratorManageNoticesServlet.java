package com.board.controller.moderator;

import com.board.model.Notice;
import com.board.model.NoticeStatus;
import com.board.service.NoticeService;
import com.board.service.impl.NoticeServiceImpl;
import com.board.utils.MailerUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ModeratorManageNoticesServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorManageNoticesServlet.class);
    private NoticeService noticeService = new NoticeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Notice> notices = noticeService.getAllNotice(String.valueOf(NoticeStatus.CREATED));
        request.setAttribute("notices", notices);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/manageNotices.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String button = request.getParameter("button");

        switch (button) {
            case "approveNotice":
                approveNotice(request, response);
                break;
            case "rejectNotice":
                rejectNotice(request, response);
                break;
            case "sendMessageWithCause":
                sendMessageWithCauseToUser(request, response);
                break;
        }
    }

    private void approveNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("noticeId");
        int id = Integer.parseInt(strId);
        Notice notice = noticeService.getById(id);
        notice.setStatus(NoticeStatus.APPROVED);
        noticeService.update(notice);
        request.setAttribute("approved", "You have successfully approved notice");
        doGet(request, response);
        LOGGER.info("Approved Notice by Moderator");
    }

    private void rejectNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("noticeId", request.getParameter("noticeId"));
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/sendCauseMessage.jsp");
        requestDispatcher.forward(request, response);
        LOGGER.info("Redirect on Reject Notice page");
    }

    private void sendMessageWithCauseToUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strId = request.getParameter("noticeId");
        int id = Integer.parseInt(strId);
        String subject = request.getParameter("subject");
        String message = request.getParameter("description");
        boolean error = getError(subject, message, request);
        if (!subject.equals("") && !message.equals("")) {
            if (error) {
                Notice notice = noticeService.getById(id);
                notice.setStatus(NoticeStatus.REJECTED);
                noticeService.update(notice);
                MailerUtil.send(notice.getUser().getEmail(), subject, message);
                request.setAttribute("rejected", "You have successfully rejected notice");
                doGet(request, response);
                LOGGER.info("Rejected notice letter");
            } else {
                LOGGER.info("Incorrect data for rejecting notice letter");
                rejectNotice(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for rejecting notice letter");
            rejectNotice(request, response);
        }
    }

    private boolean getError(String subject, String message, HttpServletRequest request) {
        boolean f = true;

        if (subject.length() < 5 || subject.length() > 50) {
            request.setAttribute("subject", "Subject length must contains from 5 to 50 symbols.");
            f = false;
        }
        if (message.length() < 10 || message.length() > 200) {
            request.setAttribute("message", "Message length must contains from 10 to 200 symbols.");
            f = false;
        }
        return f;
    }
}
