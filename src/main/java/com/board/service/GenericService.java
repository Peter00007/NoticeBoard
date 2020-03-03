package com.board.service;

public interface GenericService<T, ID> {
    T create(T t);

    T update(T t);

    void delete(ID id);

    T getById(ID id);
}
