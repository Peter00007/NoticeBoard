package com.board.dao.impl;

import com.board.dao.NoticeDao;
import com.board.dao.TypeDao;
import com.board.model.*;
import com.board.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeDaoImpl implements NoticeDao {
    private TypeDao typeDao = new TypeDaoImpl();
    private static final Logger LOGGER = Logger.getLogger(NoticeDaoImpl.class);

    private final String CREATE_NOTICE_SQL = "insert into notices(description, user_id, status, created) values (?,?,?,?)";

    private final String GET_ALL_NOTICE_SQL = "select notices.id as notice_id, notices.description as notice_description, " +
            "notices.user_id as notice_user, notices.status as notice_status, notices.created as notice_created, " +
            " users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
            " users.password as user_password, users.email as user_email, " +
            " users.status as user_status, users.confirm_registration as user_confirm_registration from notices inner join users " +
            " on notices.user_id = users.id";

    private final String GET_ALL_APPROVED_NOTICE_SQL =
            "select  notices.id as notice_id, notices.description as notice_description, " +
                    "notices.user_id as notice_user, notices.status as notice_status, notices.created as notice_created, " +
                    "users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
                    "users.password as user_password, users.email as user_email, " +
                    "users.status as user_status, users.confirm_registration  as user_confirm_registration  " +
                    "from notices inner join users on notices.user_id = users.id " +
                    "where notices.status = ?";
    private final String DELETE_NOTICE_SQL = "update notices set status = ? where id = ?";

    private final String UPDATE_NOTICE_SQL = "update notices set description = ?, user_id = ?, status = ?, created = ? where id = ?";

    private final String GET_ONE_NOTICE_SQL = "select notices.id as notice_id, notices.description as notice_description, notices.user_id as notice_user, " +
            "notices.status as notice_status, notices.created as notice_created, users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
            "users.password as user_password, users.email as user_email, " +
            "users.status as user_status, users.confirm_registration as user_confirm_registration from notices inner join users " +
            " on notices.user_id = users.id where notices.id = ?";

    private final String GET_ALL_NOTICES_BY_TYPE_SQL = "select notices.id as notice_id, notices.description as notice_description, notices.user_id as notice_user, " +
            "notices.status as notice_status, notices.created as notice_created, users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
            "users.password as user_password, users.email as user_email, " +
            "users.status as user_status, users.confirm_registration as user_confirm_registration from notices inner join users " +
            " on notices.user_id = users.id inner join type_notices " +
            "on notices.id = type_notices.notice_id inner join types t on type_notices.type_id = t.id where notices.status = ? and t.id = ?";

     private final String GET_ALL_NOTICE_BY_USER_SQL = "select notices.id as notice_id, notices.description as notice_description, notices.user_id as notice_user, " +
            "notices.status as notice_status, notices.created as notice_created, users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
            "users.password as user_password, users.email as user_email, " +
            "users.status as user_status, users.confirm_registration as user_confirm_registration from notices inner join users " +
            " on notices.user_id = users.id where notices.status = ? and user_id = ?";

    private final String GET_ALL_NOTICE_BY_CURRENT_USER_SQL = "select notices.id as notice_id, notices.description as notice_description, notices.user_id as notice_user, " +
            "notices.status as notice_status, notices.created as notice_created, users.id as user_id, users.name as user_name, users.last_name as user_last_name, " +
            "users.password as user_password, users.email as user_email, " +
            "users.status as user_status, users.confirm_registration as user_confirm_registration from notices inner join users " +
            " on notices.user_id = users.id where user_id = ?";

    private final String DELETE_FROM_TYPE_NOTICES_NOTICE_SQL = "delete from type_notices where notice_id = ?";

    @Override
    public Notice create(Notice notice) {
        int id = -1;
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.
                    prepareStatement(CREATE_NOTICE_SQL, Statement.RETURN_GENERATED_KEYS);
            ) {
                statement.setString(1, notice.getDescription());
                statement.setInt(2, notice.getUser().getId());
                statement.setString(3, String.valueOf(notice.getStatus()));
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(notice.getCreated()));
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
        LOGGER.info("Create Notice with ID = " + getById(id));
        return getById(id);
    }

    @Override
    public void delete(Integer id) {
        if (getById(id) != null) {
            try (Connection connection = ConnectionUtil.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(DELETE_NOTICE_SQL)) {
                    statement.setString(1, NoticeStatus.DELETED.toString());
                    statement.setInt(2, id);
                    statement.executeUpdate();
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
            LOGGER.info("Deleted Notice with ID = " + id);
        }
    }

    @Override
    public Notice update(Notice notice) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_NOTICE_SQL)) {
                statement.setString(1, notice.getDescription());
                statement.setInt(2, notice.getUser().getId());
                statement.setString(3, notice.getStatus().toString());
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(notice.getCreated()));
                statement.setInt(5, notice.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Updated notice = {" + getById(notice.getId()) + "}");
        return getById(notice.getId());
    }

    @Override
    public Notice getById(Integer id) {
        Notice notice = new Notice();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ONE_NOTICE_SQL)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        User user = new User();
                        notice.setId(resultSet.getInt("notice_id"));
                        notice.setDescription(resultSet.getString("notice_description"));
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLastName(resultSet.getString("user_last_name"));
                        user.setPassword(resultSet.getString("user_password"));
                        user.setEmail(resultSet.getString("user_email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                        user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                        notice.setUser(user);
                        notice.setStatus(NoticeStatus.valueOf(resultSet.getString("notice_status")));
                        notice.setCreated(resultSet.getTimestamp("notice_created").toLocalDateTime());
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get Notice by ID = " + id + ",  {" + notice + "}");
        return notice;
    }

    @Override
    public List<Notice> getAllNotice() {
        List<Notice> noticeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_ALL_NOTICE_SQL)) {
                while (resultSet.next()) {
                    Notice notice = new Notice();
                    User user = new User();
                    notice.setId(resultSet.getInt("notice_id"));
                    notice.setDescription(resultSet.getString("notice_description"));
                    user.setId(resultSet.getInt("user_id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setLastName(resultSet.getString("user_last_name"));
                    user.setPassword(resultSet.getString("user_password"));
                    user.setEmail(resultSet.getString("user_email"));
                    user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                    user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                    notice.setUser(user);
                    notice.setStatus(NoticeStatus.valueOf(resultSet.getString("notice_status")));
                    notice.setCreated(resultSet.getTimestamp("notice_created").toLocalDateTime());
                    noticeList.add(notice);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get all Notices");
        return noticeList;
    }

    @Override
    public List<Notice> getAllNotice(String status) {
        List<Notice> noticeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_APPROVED_NOTICE_SQL)) {
                statement.setString(1, status);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Notice notice = new Notice();
                        User user = new User();
                        notice.setId(resultSet.getInt("notice_id"));
                        notice.setDescription(resultSet.getString("notice_description"));
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLastName(resultSet.getString("user_last_name"));
                        user.setPassword(resultSet.getString("user_password"));
                        user.setEmail(resultSet.getString("user_email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                        user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                        notice.setUser(user);
                        notice.setStatus(NoticeStatus.valueOf(resultSet.getString("notice_status")));
                        notice.setCreated(resultSet.getTimestamp("notice_created").toLocalDateTime());
                        notice.setTypes(typeDao.getAllTypeByNotice(notice.getId()));
                        noticeList.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get all Notices with Status = {" + status + "}");
        return noticeList;
    }

    @Override
    public List<Notice> getAllByType(int type) {
        List<Notice> noticeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_NOTICES_BY_TYPE_SQL)) {
                statement.setString(1, String.valueOf(NoticeStatus.APPROVED));
                statement.setInt(2, type);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Notice notice = new Notice();
                        User user = new User();
                        notice.setId(resultSet.getInt("notice_id"));
                        notice.setDescription(resultSet.getString("notice_description"));
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLastName(resultSet.getString("user_last_name"));
                        user.setPassword(resultSet.getString("user_password"));
                        user.setEmail(resultSet.getString("user_email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                        user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                        notice.setUser(user);
                        notice.setStatus(NoticeStatus.valueOf(resultSet.getString("notice_status")));
                        notice.setCreated(resultSet.getTimestamp("notice_created").toLocalDateTime());
                        notice.setTypes(typeDao.getAllTypeByNotice(notice.getId()));
                        noticeList.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get all Notices with Type = {" + type + "}");
        return noticeList;
    }

    @Override
    public List<Notice> getAllByUser(int userId) {
        List<Notice> noticeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_NOTICE_BY_USER_SQL)) {
                statement.setString(1, String.valueOf(NoticeStatus.APPROVED));
                statement.setInt(2, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Notice notice = new Notice();
                        User user = new User();
                        notice.setId(resultSet.getInt("notice_id"));
                        notice.setDescription(resultSet.getString("notice_description"));
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLastName(resultSet.getString("user_last_name"));
                        user.setPassword(resultSet.getString("user_password"));
                        user.setEmail(resultSet.getString("user_email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                        user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                        notice.setUser(user);
                        notice.setStatus(NoticeStatus.valueOf(resultSet.getString("notice_status")));
                        notice.setCreated(resultSet.getTimestamp("notice_created").toLocalDateTime());
                        notice.setTypes(typeDao.getAllTypeByNotice(notice.getId()));
                        noticeList.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get all Active Notices by UserId = " + userId);
        return noticeList;
    }

    @Override
    public List<Notice> getAllNoticeByUser(int userId) {
        List<Notice> noticeList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ALL_NOTICE_BY_CURRENT_USER_SQL)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Notice notice = new Notice();
                        User user = new User();
                        notice.setId(resultSet.getInt("notice_id"));
                        notice.setDescription(resultSet.getString("notice_description"));
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLastName(resultSet.getString("user_last_name"));
                        user.setPassword(resultSet.getString("user_password"));
                        user.setEmail(resultSet.getString("user_email"));
                        user.setUserStatus(UserStatus.valueOf(resultSet.getString("user_status")));
                        user.setConfirmActivationCode(resultSet.getString("user_confirm_registration"));
                        notice.setUser(user);
                        notice.setStatus(NoticeStatus.valueOf(resultSet.getString("notice_status")));
                        notice.setCreated(resultSet.getTimestamp("notice_created").toLocalDateTime());
                        notice.setTypes(typeDao.getAllTypeByNotice(notice.getId()));
                        noticeList.add(notice);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Get all Notices by UserId = {" + userId + "}");
        return noticeList;
    }

    @Override
    public void deleteFromNoticeType(Notice notice) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_TYPE_NOTICES_NOTICE_SQL)) {
                statement.setInt(1, notice.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Deleted Notice {" + notice + "} from Notice_Type Table");
    }
}
