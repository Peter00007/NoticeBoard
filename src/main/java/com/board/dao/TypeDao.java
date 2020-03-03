package com.board.dao;

import com.board.model.Type;

import java.util.List;

public interface TypeDao extends GenericDao<Type, Integer>{
    List<Type> getAllType();

    List<Type> getAllTypeByNotice(int noticeId);

    void insertIntoNoticeTypes(int noticeId, int typeId);
}
