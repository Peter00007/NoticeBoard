package com.board.service.impl;

import com.board.dao.UserDao;
import com.board.dao.impl.UserDaoImpl;
import com.board.model.User;
import com.board.service.UserService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User create(User user) {
        LOGGER.info("Create User = {" + user + "}");
        return userDao.create(user);
    }

    @Override
    public User update(User user) {
        LOGGER.info("Update User = {" + user + "}");
        return userDao.update(user);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.info("Delete User = {" + id + "}");
        userDao.delete(id);
    }

    @Override
    public User getById(Integer id) {
        LOGGER.info("Get User By Id = {" + id + "}");
        return userDao.getById(id);
    }

    @Override
    public List<User> getAllUsers() {
        LOGGER.info("Get All Users");
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getAllCreatedUsers() {
        LOGGER.info("Get All Created Users");
        return userDao.getAllCreatedUsers();
    }

    @Override
    public List<User> getUsersAndModerators() {
        LOGGER.info("Get Users and Moderators");
        return userDao.getUsersAndModerators();
    }

    @Override
    public User getUserByEmail(String email) {
        LOGGER.info("Get User By Email = {" + email + "}");
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getRegisteredOrActiveUserByEmail(String email) {
        LOGGER.info("Get Registered or Active User by Email = {" + email + "}");
        return userDao.getRegisteredOrActiveUserByEmail(email);
    }

    @Override
    public Set<User> getUsersWithNotices() {
        LOGGER.info("Get Users with Notices");
        return userDao.getUsersWithNotices();
    }

    @Override
    public User getUserForAutorization(String email, String password) {
        LOGGER.info("Get User For Authorization with Email = {" + email + "}");
        return userDao.getUserForAutorization(email, password);
    }
}
