package com.board.dao.impl;

import com.board.dao.RoleDao;
import com.board.dao.UserDao;
import com.board.model.*;
import com.board.utils.ConnectionUtil;
import com.board.utils.EncodingPasswordUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private RoleDao roleDao = new RoleDaoImpl();
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    private final String CREATE_USER_SQL = "insert into users (name, last_name, password, email, status, confirm_registration) " +
            "values (?,?,?,?,?,?);";

    private final String GET_ONE_USER_SQL = "select * from users where id = ?";

    private final String DELETE_USER_SQL = "update users set status = ? where id = ?";

    private final String UPDATE_USER_SQL = "update users set name = ?, last_name = ?, password = ?, email = ?, " +
            "status = ? where id = ?";

    private final String GET_ALL_CREATED_USERS_SQL = "select * from users where status = ?";

    private final String GET_ALL_USERS_SQL = "select * from users";

    private final String GET_USER_FOR_AUTHORIZATION_SQL = "select * from users where email = ? and password = ? and status = ?";

    private final String GET_USERS_AND_MODERATORS_SQL = "select distinct id, name, last_name, password, email, status from users left join user_roles ur on users.id = ur.user_id" +
            " where role_id = 1 or role_id = 2";

    private final String GET_USER_BY_EMAIL_SQL = "select * from users where email = ? and status = ?";

    private final String GET_ACTIVE_OR_REGISTERED_USER_BY_EMAIL_SQL = "select * from users where email = ? and (status = ? or status = ?)";

    private final String GET_ALL_NOTICE_SQL = "select notices.id as notice_id, notices.description as notice_description, " +
            "notices.user_id as notice_user, notices.status as notice_status, notices.created as notice_created, " +
            " users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
            " users.password as user_password, users.email as user_email, " +
            " users.status as user_status, users.confirm_registration as user_confirm_registration from notices inner join users " +
            " on notices.user_id = users.id where users.status = 'CREATED'";

    @Override
    public User create(User user) {
        int id = -1;
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getLastName());
                statement.setString(3, EncodingPasswordUtil.encodingPassword(user.getPassword()));
                statement.setString(4, user.getEmail());
                statement.setString(5, String.valueOf(user.getUserStatus()));
                statement.setString(6, user.getConfirmActivationCode());
                statement.executeUpdate();
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        id = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Created User {" + getById(id) + "}");
        return getById(id);
    }

    @Override
    public User update(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER_SQL)) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getEmail());
                statement.setString(5, String.valueOf(user.getUserStatus()));
                statement.setInt(6, user.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Updated user {" + getById(user.getId()) + "}");
        return getById(user.getId());
    }

    @Override
    public void delete(Integer id) {
        if (getById(id) != null) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL)) {
                    statement.setString(1, String.valueOf(UserStatus.DELETED));
                    statement.setInt(2, id);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info("Deleted User with Id = {" + id + "}");
        }
    }

    @Override
    public User getById(Integer id) {
        User user = new User();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ONE_USER_SQL)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("status")));
                        user.setRoles(roleDao.getAllRoleByUser(user.getId()));
                        user.setConfirmActivationCode(resultSet.getString("confirm_registration"));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get User with Id = {" + id + "}");
        return user;
    }


    @Override
    public List<User> getAllCreatedUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CREATED_USERS_SQL)) {
                statement.setString(1, String.valueOf(UserStatus.CREATED));
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        userList.add(getById(id));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get All Created Users");
        return userList;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_SQL)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    userList.add(getById(id));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Get All Users");
        return userList;
    }

    @Override
    public User getUserForAutorization(String email, String password) {
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_USER_FOR_AUTHORIZATION_SQL)) {
                statement.setString(1, email);
                statement.setString(2, EncodingPasswordUtil.encodingPassword(password));
                statement.setString(3, String.valueOf(UserStatus.CREATED));
                try (ResultSet resultSet = statement.executeQuery()) {
                    user = new User();
                    while (resultSet.next()) {
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("status")));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        LOGGER.info("Get User {" + user + "} For LogIn");
        return user;
    }

    @Override
    public List<User> getUsersAndModerators() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_USERS_AND_MODERATORS_SQL)) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setUserStatus(UserStatus.valueOf(resultSet.getString("status")));
                    user.setRoles(roleDao.getAllRoleByUser(user.getId()));
                    boolean f = false;
                    for (Role role : user.getRoles()) {
                        if (role.getRole().equals("Admin")) {
                            f = true;
                            break;
                        }
                    }
                    if (!f) {
                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get Users and Moderators");
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = new User();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL_SQL)) {
                statement.setString(1, email);
                statement.setString(2, String.valueOf(UserStatus.CREATED));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("status")));
                        user.setRoles(roleDao.getAllRoleByUser(user.getId()));
                        user.setConfirmActivationCode(resultSet.getString("confirm_registration"));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get Active User by email {" + email + "}");
        return user;
    }

    @Override
    public User getRegisteredOrActiveUserByEmail(String email) {
        User user = new User();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ACTIVE_OR_REGISTERED_USER_BY_EMAIL_SQL)) {
                statement.setString(1, email);
                statement.setString(2, String.valueOf(UserStatus.CREATED));
                statement.setString(3, String.valueOf(UserStatus.REGISTERED));
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user.setId(resultSet.getInt("id"));
                        user.setName(resultSet.getString("name"));
                        user.setLastName(resultSet.getString("last_name"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("status")));
                        user.setRoles(roleDao.getAllRoleByUser(user.getId()));
                        user.setConfirmActivationCode(resultSet.getString("confirm_registration"));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get Registered or Active User By Email {" + "}");
        return user;
    }

    @Override
    public Set<User> getUsersWithNotices() {
        Set<User> users = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_NOTICE_SQL)) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setLastName(resultSet.getString("user_last_name"));
                    user.setPassword(resultSet.getString("user_password"));
                    user.setEmail(resultSet.getString("user_email"));
                    user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                    user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get Users, who have active notice(s)");
        return users;
    }
}
