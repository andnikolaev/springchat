package ru.nikolaev.chat.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.utility.ModelMapperToDto;
import ru.nikolaev.chat.utility.proprety.ChatPropertiesEnum;
import ru.nikolaev.chat.utility.proprety.PropertyManager;
import ru.nikolaev.chat.web.dto.EventCountDto;
import ru.nikolaev.chat.web.dto.EventDto;
import ru.nikolaev.chat.web.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@PropertySource("classpath:chat.properties")
@RestController
@RequestMapping(value = "/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ModelMapperToDto modelMapperToDto;

    @Autowired
    private PropertyManager chatPropertyManager;

    @GetMapping
    public List<EventDto> getLastEvents(@RequestBody(required = false) EventCountDto eventCountDto) {
        int count;
        if (eventCountDto != null && eventCountDto.getCount() != 0) {
            count = eventCountDto.getCount();
        } else {
            count = Integer.valueOf(chatPropertyManager.getProperty(ChatPropertiesEnum.LAST_EVENTS_COUNT));
        }
        log.debug("Start getLastEvents count = {}", count);
        List<Event> events = eventService.getLastEvents(count);
        log.debug("End getLastEvents, events = {} ", events);
        return events.stream().map(modelMapperToDto::convertToEventDto).collect(Collectors.toList());
    }
}
