package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.EventType;

import java.util.List;
import java.util.Optional;

public interface EventDao {

    List<Event> getLastEvents(int count);

    List<Event> getLastEventsByType(EventType eventType, int count);

    Optional<Event> getEventById(long id);

    Event sendEvent(Event event);

    Optional<Event> getLastEventForUserByOwnerId(long ownerUserId);

    Optional<Event> getLastEventForUserByAssigneeId(long assigneeUserId);
}
