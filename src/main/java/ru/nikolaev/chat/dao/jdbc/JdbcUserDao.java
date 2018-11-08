package ru.nikolaev.chat.dao.jdbc;

import lombok.ToString;
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

@ToString
@Repository
@PropertySource("classpath:queries.properties")
public class JdbcUserDao implements UserDao {

    private static final String ADD_NEW_USER_SQL =
            "INSERT INTO ALL_USER (NAME, PASSWORD, ROLE_ID, STATUS_ID) VALUES (?,?,?,?)";

    private static final String UPDATE_USER_SQL =
            "UPDATE ALL_USER SET NAME = ?, PASSWORD=?, ROLE_ID=?, STATUS_ID=? WHERE ID=?";

    private static final String GET_USER_BY_ID_SQL =
            "SELECT * FROM ALL_USER WHERE ID=?";

    private static final String GET_USER_BY_NAME_SQL =
            "SELECT * FROM ALL_USER WHERE NAME=?";

    private static final String GET_USER_BY_NAME_AND_PASSWORD_SQL = "SELECT * FROM ALL_USER WHERE NAME=? AND PASSWORD = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @Override
    public User addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new AddUserPreparedStatementCreator(user), keyHolder);
        User addedUser = getUserById(keyHolder.getKey().intValue());
        return addedUser;
    }

    @Override
    public User updateUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new UpdateUserPreparedStatementCreator(user), keyHolder);
        User addedUser = getUserById(keyHolder.getKey().intValue());
        return addedUser;
    }

    @Override
    public User checkAuth(String name, String password) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(GET_USER_BY_NAME_AND_PASSWORD_SQL, new UserRowMapper(), name, password);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public User getUserByName(String name) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(GET_USER_BY_NAME_SQL, new UserRowMapper(), name);
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL, new UserRowMapper(), id);
        } catch (DataAccessException e) {

        }
        return user;
    }

    class AddUserPreparedStatementCreator implements PreparedStatementCreator {

        private User user;

        AddUserPreparedStatementCreator(User user) {
            this.user = user;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//            String sql = env.getProperty("user.add");

            PreparedStatement ps = con.prepareStatement(ADD_NEW_USER_SQL, new String[]{"id"});
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
            //String sql = env.getProperty("user.update");
            PreparedStatement ps = con.prepareStatement(UPDATE_USER_SQL);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getUserRole().id());
            ps.setLong(4, user.getUserStatus().id());
            ps.setLong(5, user.getId());
            return ps;
        }
    }


}
