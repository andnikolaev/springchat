package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.web.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private AuthService authService;

    
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String registerUser(@RequestBody AuthUserDto userDto, HttpServletRequest httpServletRequest) {
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.register(name, password, httpServletRequest.getRemoteAddr());
        return user.toString();
    }


}
