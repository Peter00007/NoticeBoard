package com.board.controller.user;

import com.board.model.Notice;
import com.board.model.NoticeStatus;
import com.board.model.Type;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserAddNoticeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserAddNoticeServlet.class);
    private UserService userService = new UserServiceImpl();
    private NoticeService noticeService = new NoticeServiceImpl();
    private TypeService typeService = new TypeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Type> types = typeService.getAllType();
        request.setAttribute("types", types);
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/user/addNotice.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Notice notice = new Notice();
        String description = request.getParameter("description");
        String[] types = request.getParameterValues("typeId");
        HttpSession session = request.getSession();

        boolean error = getError(description, types, request);
        if (!description.equals("") && types != null) {
            if (error) {
                notice.setUser(userService.getById((Integer) session.getAttribute("userId")));
                notice.setCreated(LocalDateTime.now());
                notice.setStatus(NoticeStatus.CREATED);
                notice.setDescription(description);
                Notice createdNotice = noticeService.create(notice);

                Set<String> idSetString = new HashSet<>(Arrays.asList(types));
                Set<Integer> idSetInteger = new HashSet<>();
                for (String sId : idSetString) {
                    idSetInteger.add(Integer.parseInt(sId));
                }
                for (Integer type : idSetInteger) {
                    typeService.insertIntoNoticeTypes(createdNotice.getId(), type);
                }
                session.setAttribute("message", "You have successfully created notice");
                response.sendRedirect(request.getContextPath() + "/user/notices");
                LOGGER.info("Added Notice by User");
            } else {
                LOGGER.info("Incorrect data for add Notice by User");
                doGet(request, response);
            }
        } else {
            LOGGER.info("Incorrect data for add Notice by User");
            doGet(request, response);
        }
    }


    private boolean getError(String description, String[] types, HttpServletRequest request) {
        boolean f = true;
        if (description.length() < 10) {
            request.setAttribute("description", "Description length must contains at least 10 symbols and maximum 200.");
            f = false;
        }
        if (types == null) {
            request.setAttribute("typesForEdit", "Please, choose type or types.");
            f = false;
        }
        return f;
    }
}
