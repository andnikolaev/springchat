package ru.nikolaev.chat.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikolaev.chat.dao.dto.RegisteredUserDto;
import ru.nikolaev.chat.entity.User;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @PostMapping
    public void registerUser(@RequestBody RegisteredUserDto user) {

    }


}
