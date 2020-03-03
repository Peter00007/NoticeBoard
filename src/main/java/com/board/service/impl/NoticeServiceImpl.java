package com.board.service.impl;

import com.board.dao.NoticeDao;
import com.board.dao.impl.NoticeDaoImpl;
import com.board.model.Notice;
import com.board.service.NoticeService;
import org.apache.log4j.Logger;

import java.util.List;

public class NoticeServiceImpl implements NoticeService {
    private NoticeDao noticeDao = new NoticeDaoImpl();
    private Logger LOGGER = Logger.getLogger(NoticeServiceImpl.class);

    @Override
    public List<Notice> getAllNotice() {
        LOGGER.info("Get All Notices");
        return noticeDao.getAllNotice();
    }

    @Override
    public List<Notice> getAllNotice(String status) {
        LOGGER.info("Get All Notices With Status {"+ status + "}");
        return noticeDao.getAllNotice(status);
    }

    @Override
    public List<Notice> getAllByType(int type) {
        LOGGER.info("Get All Active Notices By Type + {" + type + "}");
        return noticeDao.getAllByType(type);
    }

    @Override
    public List<Notice> getAllByUser(int user) {
        LOGGER.info("Get All Active Notice By User ID = {" + user + "}");
        return noticeDao.getAllByUser(user);
    }

    @Override
    public List<Notice> getAllNoticeByUser(int user) {
        LOGGER.info("Get All Notice By User ID = {" + user + "}");
        return noticeDao.getAllNoticeByUser(user);
    }

    @Override
    public void deleteFromNoticeType(Notice notice) {
        LOGGER.info("Delete from Notice_Type By Notice = {" + notice + "}");
        noticeDao.deleteFromNoticeType(notice);
    }

    @Override
    public Notice create(Notice notice) {
        LOGGER.info("Create Notice = {" + notice + "}");
        return noticeDao.create(notice);
    }

    @Override
    public Notice update(Notice notice) {
        LOGGER.info("Update Notice = {" + notice + "}");
        return noticeDao.update(notice);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.info("Delete Notice With ID = {" + id + "}");
        noticeDao.delete(id);
    }

    @Override
    public Notice getById(Integer id) {
        LOGGER.info("Get Notice By Id = {" + id + "}");
        return noticeDao.getById(id);
    }
}
