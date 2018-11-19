package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.MessageDto;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.BadRequestDataException;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.web.service.MessageService;
import ru.nikolaev.chat.web.storage.OnlineUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/messages")
public class MessageController {
    @Autowired
    private OnlineUser onlineUser;
    @Autowired
    private MessageService messageService;

    @PostMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    @ResponseStatus(HttpStatus.OK)
    public Event sendMessage(@Valid @RequestBody MessageDto messageDto, Errors validationErrors, HttpServletRequest httpServletRequest) {
        if (validationErrors.hasErrors()) {
            new ExceptionThrower(new BadRequestDataException()).addValidationsError(validationErrors).throwException();
        }
        Event event = messageService.sendMessage(onlineUser.getUser(), messageDto.getText(), httpServletRequest.getRemoteAddr());
        return event;
    }

    @GetMapping
    public List<Event> getLastMessages() {
        //TODO убрать хардкод 20 и сделать получение и дефолтное значение
        return messageService.getLastMessages(20);
    }
}
