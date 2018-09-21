package ru.nikolaev.chat.dao.jdbc;

import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.EventType;
import ru.nikolaev.chat.entity.User;

public class JdbcEventDao implements EventDao {
    @Override
    public void sendEvent(User owner, EventType eventType, String message) {
        sendEvent(owner, eventType, message, null);
    }

    @Override
    public void sendEvent(User owner, EventType eventType, String message, User assignee) {

    }
}
