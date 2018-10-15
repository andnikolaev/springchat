package ru.nikolaev.chat.dao.jdbc.mapper;


import org.springframework.jdbc.core.RowMapper;
import ru.nikolaev.chat.entity.Message;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        Message message = new Message();
        message.setUserId(rs.getLong("owner_id"));
        message.setText(rs.getString("message"));
        message.setTimestamp(rs.getTimestamp("time_stamp").toLocalDateTime());
        return message;
    }
}
