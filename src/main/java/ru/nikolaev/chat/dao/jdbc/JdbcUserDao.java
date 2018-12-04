package ru.nikolaev.chat.dao.jdbc;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.dao.jdbc.mapper.UserRowMapper;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.exception.DataBaseAccessFailedException;
import ru.nikolaev.chat.exception.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@ToString
@Repository
@PropertySource("classpath:queries.properties")
public class JdbcUserDao implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_QUERY_CHECK_USER = "SELECT * FROM ALL_USER WHERE NAME=? AND PASSWORD = ?";
    private static final String SQL_QUERY_GET_USER_BY_NAME = "SELECT * FROM ALL_USER WHERE NAME=?";
    private static final String SQL_QUERY_GET_USER_BY_ID = "SELECT * FROM ALL_USER WHERE ID=?";
    private static final String SQL_QUERY_INSERT_NEW_USER = "INSERT INTO ALL_USER (NAME, PASSWORD, ROLE_ID, STATUS_ID) VALUES (?,?,?,?)";
    private static final String SQL_QUERY_UPDATE_USER = "UPDATE ALL_USER SET NAME = ?, ROLE_ID=?, STATUS_ID=? WHERE ID=?";

    @Override
    public User addUser(User user) {
        log.info("Start addUser = {}", user);
        User addedUser;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new AddUserPreparedStatementCreator(user), keyHolder);
            addedUser = getUserById(keyHolder.getKey().intValue()).orElse(null);
        } catch (DataAccessException e) {
            log.error("Failed add user", e);
            throw new DataBaseAccessFailedException();
        }
        log.info("End addUser = {}", addedUser);
        return addedUser;
    }

    @Override
    public User updateUser(User user) {
        log.info("Start updateUser");
        log.debug("Updating user = {}", user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        User updatedUser;
        try {
            jdbcTemplate.update(new UpdateUserPreparedStatementCreator(user), keyHolder);
            updatedUser = getUserById(user.getId()).orElse(null);
        } catch (DataAccessException e) {
            log.error("Data access error.", e);
            throw new DataBaseAccessFailedException();
        }


        log.debug("Updated user = {}", updatedUser);
        log.info("End updateUser");
        return updatedUser;
    }

    @Override
    public User checkAuth(String name, String password) {
        log.info("Start checkAuth for user with name = {}", name);

        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_QUERY_CHECK_USER, new UserRowMapper(), name, password);
        } catch (EmptyResultDataAccessException e) {
            log.debug("EmptyResultDataAccessException", e);
            log.warn("User with name = {} and password not found.", name);
            throw new UserNotFoundException();
        } catch (DataAccessException e) {
            log.error("Data access error.", e);
            throw new DataBaseAccessFailedException();
        }

        log.debug("User = ", user);
        log.info("End checkAuth for user with name = {}", name);
        return user;
    }

    @Override
    public Optional<User> getUserByName(String name) {
        log.info("Start getUserByName for user with name = {}", name);

        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = Optional.of(jdbcTemplate.queryForObject(SQL_QUERY_GET_USER_BY_NAME, new UserRowMapper(), name));
        } catch (EmptyResultDataAccessException e) {
            log.debug("EmptyResultDataAccessException", e);
            log.warn("User with name = {} not exist.", name);
        } catch (DataAccessException e) {
            log.error("Data access error.", e);
            throw new DataBaseAccessFailedException();
        }

        log.info("End getUserByName for user with name = {} ", name);
        return optionalUser;
    }

    @Override
    public Optional<User> getUserById(long id) {
        log.debug("Start getUserById for user with id = ", id);

        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = Optional.of(jdbcTemplate.queryForObject(SQL_QUERY_GET_USER_BY_ID, new UserRowMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            log.debug("EmptyResultDataAccessException", e);
            log.warn("User with id = {} not found.", id);
        } catch (DataAccessException e) {
            log.error("Data access error.", e);
            throw new DataBaseAccessFailedException();
        }

        log.debug("End getUserById for user with id = {}", id);
        return optionalUser;
    }

    class AddUserPreparedStatementCreator implements PreparedStatementCreator {

        private User user;

        AddUserPreparedStatementCreator(User user) {
            this.user = user;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(SQL_QUERY_INSERT_NEW_USER, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getUserRole().id());
            ps.setLong(4, user.getUserStatus().id());
            return ps;
        }
    }

    class UpdateUserPreparedStatementCreator implements PreparedStatementCreator {
        private User user;

        UpdateUserPreparedStatementCreator(User user) {
            this.user = user;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(SQL_QUERY_UPDATE_USER, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setLong(2, user.getUserRole().id());
            ps.setLong(3, user.getUserStatus().id());
            ps.setLong(4, user.getId());
            return ps;
        }
    }


}
