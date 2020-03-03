package com.board.dao;

import com.board.model.Notice;

import java.util.List;

public interface NoticeDao extends GenericDao<Notice, Integer> {
    List<Notice> getAllNotice();

    List<Notice> getAllNotice(String status);

    List<Notice> getAllByType(int type);

    List<Notice> getAllByUser(int user);

    List<Notice> getAllNoticeByUser(int user);

    void deleteFromNoticeType(Notice notice);
}
