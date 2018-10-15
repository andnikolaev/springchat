package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;

public interface EventDao {
    int sendEvent(User owner, EventType eventType, String message, String ip);

    int sendEvent(User owner, EventType eventType, String message,  String ip, User assignee);

}
