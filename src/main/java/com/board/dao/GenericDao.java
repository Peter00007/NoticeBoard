package com.board.dao;

public interface GenericDao<T, ID> {
    T create(T t);

    T update(T t);

    void delete(ID id);

    T getById(ID id);
}
