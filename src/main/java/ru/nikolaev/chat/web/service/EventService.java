package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDao eventDao;

    public List<Event> getLastEvents(int count) {
        List<Event> eventList = eventDao.getLastEvents(count);
        return eventList;
    }

    public Event sendEvent(User owner, EventType eventType, String ownerIp) {
        return this.sendEvent(owner, null, eventType, ownerIp);
    }

    public Event sendEvent(User owner, User assignee, EventType eventType, String ownerIp) {
        return this.sendEvent(owner, assignee, eventType, null, ownerIp);
    }

    public Event sendEvent(User owner, EventType eventType, String message, String ownerIp) {
        return this.sendEvent(owner, null, eventType, message, ownerIp);
    }


    public Event sendEvent(User owner, User assignee, EventType eventType, String message, String ownerIp) {
        Event event = new Event();
        event.setOwner(owner);
        event.setAssignee(assignee);
        event.setEventType(eventType);
        event.setMessage(message);
        event.setIp(ownerIp);
        event = eventDao.sendEvent(event);
        return event;
    }

    public Event getLastEventForUser(User user) {
        Event ownerEvent = eventDao.getLastEventForUserByOwnerId(user.getId());
        Event assigneeEvent = eventDao.getLastEventForUserByAssigneeId(user.getId());
        if (assigneeEvent == null) {
            return ownerEvent;
        }
        return ownerEvent.getTimestamp().compareTo(assigneeEvent.getTimestamp()) > 0 ? ownerEvent : assigneeEvent;
    }
}
