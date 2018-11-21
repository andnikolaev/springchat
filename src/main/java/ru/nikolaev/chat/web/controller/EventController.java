package ru.nikolaev.chat.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.web.dto.EventDto;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.utility.ModelMapperToDto;
import ru.nikolaev.chat.web.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/events")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapperToDto modelMapperToDto;


    @GetMapping
    public List<EventDto> getLastEvents() {
        //TODO перенести в файл настроек
        int count = 20;
        log.debug("Start getLastEvents count:" + count);
        List<Event> events = eventService.getLastEvents(count);
        log.debug("End getLastEvents, events: " + events);
        return events.stream().map(modelMapperToDto::convertToEventDto).collect(Collectors.toList());
    }
}
