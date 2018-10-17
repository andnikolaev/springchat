package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;

import java.util.List;

public interface EventDao {
    int sendEvent(User owner, EventType eventType, String message, String ip);

    int sendEvent(User owner, EventType eventType, String message, String ip, User assignee);

    List<Event> getLastEvents(int count);
}
