package com.board.service;

import com.board.model.Type;

import java.util.List;

public interface TypeService extends GenericService<Type, Integer> {
    List<Type> getAllType();

    List<Type> getAllTypeByNotice(int noticeId);

    void insertIntoNoticeTypes(int noticeId, int typeId);
}
