package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Event> getOnlineUsers(@RequestBody EventCountDto eventCountDto) {
        return eventService.getLastEvents(eventCountDto.getCount());
    }
}
