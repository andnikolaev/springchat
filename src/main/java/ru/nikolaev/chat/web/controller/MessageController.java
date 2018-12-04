package ru.nikolaev.chat.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.BadRequestDataException;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.utility.ModelMapperToDto;
import ru.nikolaev.chat.utility.proprety.ChatPropertiesEnum;
import ru.nikolaev.chat.utility.proprety.PropertyManager;
import ru.nikolaev.chat.web.dto.EventDto;
import ru.nikolaev.chat.web.dto.MessageCountDto;
import ru.nikolaev.chat.web.dto.MessageTextDto;
import ru.nikolaev.chat.web.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@PropertySource("classpath:chat.properties")
@RestController
@RequestMapping(value = "/api/messages")
public class MessageController {
    @Autowired
    private User onlineUser;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ModelMapperToDto modelMapperToDto;

    @Autowired
    private PropertyManager chatPropertyManager;

    @PostMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    @ResponseStatus(HttpStatus.OK)
    public EventDto sendMessage(@Valid @RequestBody MessageTextDto messageDto, Errors validationErrors, HttpServletRequest httpServletRequest) {
        log.info("Start sendMessage {}", messageDto);
        if (validationErrors.hasErrors()) {
            log.warn("Throwing new exception BadRequestDataException with validators error");
            log.debug("Validators error = {}", validationErrors);
            new ExceptionThrower(new BadRequestDataException()).addValidationsError(validationErrors).throwException();
        }
        Event event = messageService.sendMessage(onlineUser, messageDto.getText(), httpServletRequest.getRemoteAddr());
        log.info("End sendMessage, event = {}", event);
        return modelMapperToDto.convertToEventDto(event);
    }

    @GetMapping
    public List<EventDto> getLastMessages(@RequestBody(required = false) MessageCountDto messageCountDto) {
        int count;
        if (messageCountDto != null && messageCountDto.getCount() != 0) {
            count = messageCountDto.getCount();
        } else {
            count = Integer.valueOf(chatPropertyManager.getProperty(ChatPropertiesEnum.LAST_MESSAGES_COUNT));
        }
        log.debug("Start getLastEvents count = {}", count);
        List<Event> events = messageService.getLastMessages(count);
        log.debug("End getLastEvents events = {}", events);
        return events.stream().map(modelMapperToDto::convertToEventDto).collect(Collectors.toList());
    }

}
