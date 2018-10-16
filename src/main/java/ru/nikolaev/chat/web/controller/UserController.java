package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.dao.dto.UserStatusDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.web.UserSession;
import ru.nikolaev.chat.web.UserSessionStorageHandler;
import ru.nikolaev.chat.web.service.AdminService;
import ru.nikolaev.chat.web.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserSessionStorageHandler userSessionStorageHandler;

    @Autowired
    private UserSession userSession;

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User registerUser(@RequestBody AuthUserDto userDto, HttpServletRequest httpServletRequest) {
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.register(name, password, httpServletRequest.getRemoteAddr());
        return user;
    }

    @DeleteMapping("/{id}/session")
    public void kickUser(@PathVariable long id, HttpServletRequest httpServletRequest) {
        adminService.kickUser(userSession.getUser().getId(), id, httpServletRequest.getRemoteAddr());
        userSessionStorageHandler.invalidate(new User(id));
    }

    @PostMapping("/{id}/session")
    public void updateUserStatus(@PathVariable long id, @RequestBody UserStatusDto userStatusDto, HttpServletRequest httpServletRequest) {
        adminService.updateUserStatus(userSession.getUser().getId(), id, EventType.BANNED, UserStatus.getUserStatusById(userStatusDto.getStatusId()), httpServletRequest.getRemoteAddr());
        userSessionStorageHandler.invalidate(new User(id));
    }


}
