package ru.nikolaev.chat.dao.jdbc;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.MessageDao;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@ToString
@Repository
@PropertySource("classpath:queries.properties")
public class JdbcUserDao implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @Override
    //TODO убрать тестовый хардкод
    public int addUser(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            String sql = env.getProperty("user.add");
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, 1);
            ps.setInt(4, 1);
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int login(User user) {
        return 0;
    }

    @Override
    public void logout(User user) {

    }

    @Override
    public User getUserByName(String name) {
        return null;
    }

    @Override
    public List<User> getAllLoggedUsers() {
        return null;
    }
}
