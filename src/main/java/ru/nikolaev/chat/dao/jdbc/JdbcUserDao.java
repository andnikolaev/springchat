package ru.nikolaev.chat.dao.jdbc;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.dao.jdbc.mapper.UserRowMapper;
import ru.nikolaev.chat.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@ToString
@Repository
@PropertySource("classpath:queries.properties")
public class JdbcUserDao implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @Override
    public User addUser(User user) {
        log.info("Start addUser");
        log.debug("Adding user: " + user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new AddUserPreparedStatementCreator(user), keyHolder);
        User addedUser = getUserById(keyHolder.getKey().intValue());

        log.debug("Added user:" + addedUser);
        log.info("End addUser");
        return addedUser;
    }

    @Override
    public User updateUser(User user) {
        log.info("Start updateUser");
        log.debug("Updating user: " + user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new UpdateUserPreparedStatementCreator(user), keyHolder);
        } catch (DataAccessException e) {
            log.warn("User for updating not found");
        }
        User updatedUser = getUserById(user.getId());

        log.debug("Updated user:" + updatedUser);
        log.info("End updateUser");
        return updatedUser;
    }

    @Override
    public User checkAuth(String name, String password) {
        log.info("Start checkAuth for user with name " + name);

        User user = null;
        try {
            String sqlQuery = env.getProperty("user.get.by.name.and.password");
            user = jdbcTemplate.queryForObject(sqlQuery, new UserRowMapper(), name, password);
        } catch (DataAccessException e) {
            log.warn("User with this name and password not found.");
        }

        log.debug("User:" + user);
        log.info("End checkAuth for user with name " + name);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        log.info("Start getUserByName for user with name " + name);

        User user = null;
        try {
            String sqlQuery = env.getProperty("user.get.by.name");
            user = jdbcTemplate.queryForObject(sqlQuery, new UserRowMapper(), name);
        } catch (DataAccessException e) {
            log.warn("User with name " + name);
        }

        log.debug("User:" + user);
        log.info("End getUserByName for user with name " + name);
        return user;
    }

    @Override
    public User getUserById(long id) {
        log.debug("Start getUserById for user with id " + id);

        User user = null;
        try {
            String sqlQuery = env.getProperty("user.get.by.id");
            user = jdbcTemplate.queryForObject(sqlQuery, new UserRowMapper(), id);
        } catch (DataAccessException e) {
            log.warn("User with id " + id + " not found.");
        }

        log.debug("End getUserById for user with id " + id + " User: " + user);
        return user;
    }

    class AddUserPreparedStatementCreator implements PreparedStatementCreator {

        private User user;

        AddUserPreparedStatementCreator(User user) {
            this.user = user;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            String sqlQuery = env.getProperty("user.new.add");
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getUserRole().id());
            ps.setLong(4, user.getUserStatus().id());
            return ps;
        }
    }

    class UpdateUserPreparedStatementCreator implements PreparedStatementCreator {
        private User user;

        public UpdateUserPreparedStatementCreator(User user) {
            this.user = user;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            String sqlQuery = env.getProperty("user.update");
            PreparedStatement ps = con.prepareStatement(sqlQuery, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setLong(2, user.getUserRole().id());
            ps.setLong(3, user.getUserStatus().id());
            ps.setLong(4, user.getId());
            return ps;
        }
    }


}
