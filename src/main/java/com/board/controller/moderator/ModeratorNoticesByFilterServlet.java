package com.board.controller.moderator;

import com.board.model.Notice;
import com.board.model.Type;
import com.board.model.User;
import com.board.service.NoticeService;
import com.board.service.TypeService;
import com.board.service.UserService;
import com.board.service.impl.NoticeServiceImpl;
import com.board.service.impl.TypeServiceImpl;
import com.board.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ModeratorNoticesByFilterServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ModeratorNoticesByFilterServlet.class);
    private NoticeService noticeService = new NoticeServiceImpl();
    private TypeService typeService = new TypeServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        duplicateCode(request);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/filter.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        duplicateCode(request);
        String[] type = request.getParameterValues("type");
        String[] user = request.getParameterValues("user");
        Set<Notice> notices = new HashSet<>();

        if (type != null) {
            Set<String> idSetString = new HashSet<>(Arrays.asList(type));
            Set<Integer> idSetInteger = new HashSet<>();
            for (String sId : idSetString) {
                idSetInteger.add(Integer.parseInt(sId));
            }

            for (Integer noticeId : idSetInteger) {
                List<Notice> noticeToSave = noticeService.getAllByType(noticeId);
                notices.addAll(noticeToSave);
            }
        }
        if (user != null) {
            Set<String> idSetString = new HashSet<>(Arrays.asList(user));
            Set<Integer> idSetInteger = new HashSet<>();
            for (String sId : idSetString) {
                idSetInteger.add(Integer.parseInt(sId));
            }

            for (Integer userId : idSetInteger) {
                List<Notice> noticeToSave = noticeService.getAllByUser(userId);
                notices.addAll(noticeToSave);
            }
        }
        request.setAttribute("list", notices);
        if (user != null || type != null) {
            request.setAttribute("url", request.getRequestURI());
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/moderator/allNoticesByFilter.jsp");
            requestDispatcher.forward(request, response);
            LOGGER.info("Getting filtering results by users/types");
        } else {
            LOGGER.info("Please, select the option(s) for filtering");
            request.setAttribute("error", "Please, select the option(s) for filtering");
            doGet(request, response);
        }
    }

    private void duplicateCode(HttpServletRequest request) {
        request.setAttribute("url", request.getRequestURI());
        List<Type> types = typeService.getAllType();
        request.setAttribute("types", types);
        Set<User> users = userService.getUsersWithNotices();
        request.setAttribute("users", users);
    }
}
