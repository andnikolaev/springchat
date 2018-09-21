package ru.nikolaev.chat.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nikolaev.chat.dao.MessageDao;
import ru.nikolaev.chat.entity.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
@PropertySource("classpath:queries.properties")
public class JdbcMessageDao implements MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Message> getLastMessages(int count) {
        List<Message> messageList = new LinkedList<>();

        return messageList;
    }

    @Override
    public void sendMessage(Message message) {

    }
}
