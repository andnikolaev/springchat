package ru.nikolaev.chat.web.service;

import org.springframework.stereotype.Service;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.Message;

import java.util.List;

@Service
public class EventService {
    public List<Event> getLastEvents(int count) {
        List<Event> eventList = null;
        return eventList;
    }
}
