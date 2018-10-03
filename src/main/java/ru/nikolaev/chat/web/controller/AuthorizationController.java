package ru.nikolaev.chat.web.controller;


import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.dao.dto.RegisteredUserDto;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthorizationController {

    @PostMapping
    public void login(@RequestBody RegisteredUserDto userDto) {

    }

    @DeleteMapping
    public void logout() {
        
    }
}
