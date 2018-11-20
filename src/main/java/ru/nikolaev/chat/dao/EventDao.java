package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;

import java.util.List;

public interface EventDao {

    List<Event> getLastEvents(int count);

    List<Event> getLastEventsByType(EventType eventType, int count);

    Event getEventById(long id);

    Event sendEvent(Event event);

    Event getLastEventForUserByOwnerId(long ownerUserId);

    Event getLastEventForUserByAssigneeId(long assigneeUserId);
}
