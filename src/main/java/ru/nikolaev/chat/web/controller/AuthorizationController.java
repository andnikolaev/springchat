package ru.nikolaev.chat.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.entity.UserRole;
import ru.nikolaev.chat.entity.UserStatus;
import ru.nikolaev.chat.web.UserSession;
import ru.nikolaev.chat.web.UserSessionHandler;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthorizationController {

    @Autowired
    UserSessionHandler userSessionHandler;

    @PostMapping()
    @Permission(role = UserRole.ANONYMOUS)
    public void login(@RequestBody AuthUserDto userDto, @Autowired User user) {
        System.out.println(userDto);

    }

    @DeleteMapping
    public void logout() {

    }
}
