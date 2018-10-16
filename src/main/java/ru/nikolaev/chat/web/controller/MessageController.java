package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.MessageDto;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.web.UserSession;
import ru.nikolaev.chat.web.service.MessageService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/messages")
public class MessageController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private MessageService messageService;

    @PostMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    @ResponseStatus(HttpStatus.OK)
    public void sendMessage(@RequestBody MessageDto messageDto, HttpServletRequest httpServletRequest) {
        messageService.sendMessage(messageDto.getUserId(), messageDto.getText(), httpServletRequest.getRemoteAddr());
    }

    @GetMapping
    public String getOnlineUsers() {
        //TODO убрать хардкод 20 и сделать получение и дефолтное значение
        return messageService.getLastMessages(20).toString();
    }
}
