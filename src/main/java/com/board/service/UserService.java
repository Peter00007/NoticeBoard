package com.board.service;

import com.board.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends GenericService<User, Integer> {
    List<User> getAllUsers();

    List<User> getAllCreatedUsers();

    List<User> getUsersAndModerators();

    User getUserByEmail(String email);

    User getRegisteredOrActiveUserByEmail(String email);

    Set<User> getUsersWithNotices();

    User getUserForAutorization(String email, String password);
}
