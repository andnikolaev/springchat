package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.dao.dto.EventCountDto;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.web.service.EventService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getLastEvents() {
        //TODO перенести в файл настроек
        int count = 20;
        return eventService.getLastEvents(count);
    }
}
