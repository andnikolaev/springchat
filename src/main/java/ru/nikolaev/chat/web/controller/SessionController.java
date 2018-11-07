package ru.nikolaev.chat.web.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.web.service.AuthService;
import ru.nikolaev.chat.web.storage.OnlineUser;
import ru.nikolaev.chat.web.storage.OnlineUserManager;
import ru.nikolaev.chat.web.storage.OnlineUserSessionStorage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/api/sessions")
public class SessionController {
    @Autowired
    private OnlineUser onlineUser;

    @Autowired
    private OnlineUserManager onlineUserManager;
    @Autowired
    private AuthService authService;

    @PostMapping
    @Permission(role = UserRole.ANONYMOUS)
    @ResponseStatus(HttpStatus.OK)
    public User login(@RequestBody AuthUserDto userDto, HttpServletRequest httpServletRequest) {
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.login(name, password, httpServletRequest.getRemoteAddr());
        BeanUtils.copyProperties(user, onlineUser);
        onlineUserManager.addUser(onlineUser);
        return user;
    }

    @DeleteMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest httpServletRequest) {
        // authService.logout(userSession.getUser(), httpServletRequest.getRemoteAddr());
        httpServletRequest.getSession().invalidate();
        onlineUserManager.removeUser(onlineUser);
    }

    @GetMapping
    public List<OnlineUser> getOnlineUsers() {
        return onlineUserManager.getUsers();
    }
}