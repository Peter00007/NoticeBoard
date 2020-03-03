package com.board.dao.impl;

import com.board.dao.RoleDao;
import com.board.model.Role;
import com.board.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    private static final Logger LOGGER = Logger.getLogger(RoleDaoImpl.class);

    private final String GET_ALL_ROLES_SQL = "select * from roles";
    private final String INSERT_INTO_USER_ROLES_SQL = "insert into user_roles(user_id, role_id) values (?, ?)";
    private final String DELETE_FROM_USER_ROLES_SQL = "delete from user_roles where user_id = ?";
    private final String GET_ALL_ROLES_BY_USER_SQL = "select * from roles inner join user_roles on " +
            "roles.id = user_roles.role_id where user_id = ?";

    @Override
    public List<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_ROLES_SQL)) {
                while (resultSet.next()) {
                    Role role = new Role();
                    role.setId(resultSet.getInt("id"));
                    role.setRole(resultSet.getString("role"));
                    roleList.add(role);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get All Roles");
        return roleList;
    }

    @Override
    public void insertIntoUserRoles(int userId, int roleId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USER_ROLES_SQL)) {
                statement.setInt(1, userId);
                statement.setInt(2, roleId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Insert Role with Id = {" + roleId + "} and  User with Id = {" + userId + "} into User_Roles Table");
    }

    @Override
    public void deleteFromUserRoles(int userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_USER_ROLES_SQL)) {
                statement.setInt(1, userId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Deleted Role and User with Id = {" + userId + "} from User_Roles Table");

    }

    @Override
    public List<Role> getAllRoleByUser(int userId) {
        List<Role> roleList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ROLES_BY_USER_SQL)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Role role = new Role();
                        role.setId(resultSet.getInt("id"));
                        role.setRole(resultSet.getString("role"));
                        roleList.add(role);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get All Roles by User with Id = {" + userId + "}");
        return roleList;
    }
}
