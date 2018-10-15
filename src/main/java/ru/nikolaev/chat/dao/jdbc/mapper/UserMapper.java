package ru.nikolaev.chat.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.entity.UserRole;
import ru.nikolaev.chat.entity.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setUserRole(UserRole.getEventTypeById(rs.getInt("role_id")));
        user.setUserStatus(UserStatus.getEventTypeById(rs.getInt("status_id")));
        return user;
    }
}
