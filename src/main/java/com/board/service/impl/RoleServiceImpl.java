package com.board.service.impl;

import com.board.dao.RoleDao;
import com.board.dao.impl.RoleDaoImpl;
import com.board.model.Role;
import com.board.service.RoleService;
import org.apache.log4j.Logger;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao = new RoleDaoImpl();
    private static final Logger LOGGER = Logger.getLogger(RoleServiceImpl.class);

    @Override
    public List<Role> getAllRoles() {
        LOGGER.info("Get All Roles");
        return roleDao.getAllRoles();
    }

    @Override
    public void insertIntoUserRoles(int userId, int roleId) {
        LOGGER.info("Insert into User_Roles User with ID = {" + userId + "} and Role with ID = {" + "}");
        roleDao.insertIntoUserRoles(userId, roleId);
    }

    @Override
    public void deleteFromUserRoles(int userId) {
        LOGGER.info("Delete from User_Roles with User ID = {" + userId + "}");
        roleDao.deleteFromUserRoles(userId);
    }

    @Override
    public List<Role> getAllRoleByUser(int userId) {
        LOGGER.info("Get all Roles by User ID ={" + userId + "}");
        return roleDao.getAllRoleByUser(userId);
    }
}
