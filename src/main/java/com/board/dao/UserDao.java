package com.board.dao;

import com.board.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao extends GenericDao<User, Integer> {
    List<User> getAllUsers();

    List<User> getAllCreatedUsers();

    User getUserForAutorization(String email, String password);

    List<User> getUsersAndModerators();

    User getUserByEmail(String email);

    User getRegisteredOrActiveUserByEmail(String email);

    Set<User> getUsersWithNotices();
}
