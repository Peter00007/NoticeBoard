package com.board.service;

import com.board.model.Notice;

import java.util.List;
import java.util.Set;

public interface NoticeService extends GenericService<Notice, Integer> {
    List<Notice> getAllNotice();

    List<Notice> getAllNotice(String status);

    List<Notice> getAllByType(int type);

    List<Notice> getAllByUser(int user);

    List<Notice> getAllNoticeByUser(int user);

    void deleteFromNoticeType(Notice notice);
}
