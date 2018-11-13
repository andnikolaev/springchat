package ru.nikolaev.chat.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("ID"));
        user.setName(rs.getString("NAME"));
        user.setUserRole(UserRole.getUserRoleById(rs.getInt("ROLE_ID")));
        user.setUserStatus(UserStatus.getUserStatusById(rs.getInt("STATUS_ID")));
        return user;
    }
}
