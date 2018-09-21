package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.EventType;
import ru.nikolaev.chat.entity.User;

public interface EventDao {
    int sendEvent(User owner, EventType eventType, String message);

    int sendEvent(User owner, EventType eventType, String message, User assignee);

}
