package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.EventType;
import ru.nikolaev.chat.entity.User;

public interface EventDao {
    void sendEvent(User owner, EventType eventType, String message);

    void sendEvent(User owner, EventType eventType, String message, User assignee);

}
