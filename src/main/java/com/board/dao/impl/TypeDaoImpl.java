package com.board.dao.impl;

import com.board.dao.TypeDao;
import com.board.model.Type;
import com.board.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeDaoImpl implements TypeDao {
    private static final Logger LOGGER = Logger.getLogger(TypeDaoImpl.class);

    private final String GET_ALL_TYPE_SQL = "select * from types";
    private final String GET_ALL_TYPE_BY_NOTICE_SQL = "select * from types inner join type_notices on types.id = type_notices.type_id " +
            "inner join notices notices on type_notices.notice_id = notices.id where notices.id = ?";
    private final String ADD_INTO_NOTICE_TYPES_SQL = "insert into type_notices(notice_id, type_id) values (?, ?)";
    private final String GET_TYPE_BY_ID_SQL = "select * from  types where id = ?";
    private final String UPDATE_TYPE_SQL = "update types set type = ? where id = ?";
    private final String DELETE_FROM_TYPE_SQL = "delete from types where id = ?";
    private final String DELETE_FROM_NOTICE_TYPE_SQL = "delete from type_notices where type_id = ?";
    private final String CREATE_TYPE_SQL = "insert into types(type) values(?) ";

    @Override
    public List<Type> getAllType() {
        List<Type> typeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_TYPE_SQL)) {
                while (resultSet.next()) {
                    Type type = new Type();
                    type.setId(resultSet.getInt("id"));
                    type.setType(resultSet.getString("type"));
                    typeList.add(type);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get All Types");
        return typeList;
    }

    @Override
    public List<Type> getAllTypeByNotice(int noticeId) {
        List<Type> typeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_TYPE_BY_NOTICE_SQL)) {
                statement.setInt(1, noticeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Type type = new Type();
                        type.setId(resultSet.getInt("id"));
                        type.setType(resultSet.getString("type"));
                        typeList.add(type);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get All Type by Notice with Id = { " + noticeId + "}");
        return typeList;
    }

    @Override
    public void insertIntoNoticeTypes(int noticeId, int typeId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(ADD_INTO_NOTICE_TYPES_SQL)) {
                statement.setInt(1, noticeId);
                statement.setInt(2, typeId);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Insert into Notice_Types Table Notice with Id = {" + noticeId + "} and Type with Id = {" + typeId + "}");
    }

    @Override
    public Type create(Type type) {
        int id = -1;
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(CREATE_TYPE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, type.getType());
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
        LOGGER.info("Created Type {" + getById(id) + "}");
        return getById(id);
    }

    @Override
    public Type update(Type type) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_TYPE_SQL)) {
                statement.setString(1, type.getType());
                statement.setInt(2, type.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Updated Type {" + getById(type.getId()) + "}");
        return getById(type.getId());
    }

    @Override
    public void delete(Integer id) {
        if (getById(id) != null) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_NOTICE_TYPE_SQL)) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                }
                try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_TYPE_SQL)) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info("Deleted Type with Id = {" + id + "}");
        }
    }

    @Override
    public Type getById(Integer id) {
        Type type = new Type();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_TYPE_BY_ID_SQL)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        type.setId(resultSet.getInt("id"));
                        type.setType(resultSet.getString("type"));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get Type with Id = {" + id + "}");
        return type;
    }
}
