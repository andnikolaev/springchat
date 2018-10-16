package ru.nikolaev.chat.web.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.web.UserSession;
import ru.nikolaev.chat.web.UserSessionStorageHandler;
import ru.nikolaev.chat.web.service.AuthService;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/sessions")
public class AuthorizationController {
    @Autowired
    private UserSession userSession;
    @Autowired
    private UserSessionStorageHandler userSessionStorageHandler;
    @Autowired
    private AuthService authService;

    @PostMapping
    @Permission(role = UserRole.ANONYMOUS)
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody AuthUserDto userDto, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.login(name, password, httpServletRequest.getRemoteAddr());
        BeanUtils.copyProperties(user, userSession.getUser());
        userSessionStorageHandler.initUserSession(userSession, httpSession);
    }

    @DeleteMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    public void logout(HttpServletRequest httpServletRequest) {
        authService.logout(userSession.getUser(), httpServletRequest.getRemoteAddr());
        userSessionStorageHandler.invalidate(userSession);
    }

    @GetMapping
    public String getOnlineUsers() {
        return userSessionStorageHandler.getUsers().toString();
    }
}
