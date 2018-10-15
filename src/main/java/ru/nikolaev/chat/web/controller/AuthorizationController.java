package ru.nikolaev.chat.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.entity.UserRole;
import ru.nikolaev.chat.web.UserSessionHandler;
import ru.nikolaev.chat.web.service.AuthService;
import ru.nikolaev.chat.web.service.UserService;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthorizationController {

    @Autowired
    private UserSessionHandler userSessionHandler;
    @Autowired
    private AuthService authService;

    @Autowired
    private User user;

    @PostMapping()
    @Permission(role = UserRole.ANONYMOUS)
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody AuthUserDto userDto) {
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.login(name, password);

    }

    @DeleteMapping
    public void logout() {

    }
}
