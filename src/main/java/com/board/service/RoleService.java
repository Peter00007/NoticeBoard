package com.board.service;

import com.board.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    void insertIntoUserRoles(int userId, int roleId);

    void deleteFromUserRoles(int userId);

    List<Role> getAllRoleByUser(int userId);
}
