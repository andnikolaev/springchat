package ru.nikolaev.chat.web.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.dto.AuthUserDto;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.BadRequestDataException;
import ru.nikolaev.chat.exception.ChatExceptionEnum;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.exception.UserAlreadyExistException;
import ru.nikolaev.chat.web.service.AuthService;
import ru.nikolaev.chat.web.storage.OnlineUser;
import ru.nikolaev.chat.web.storage.OnlineUserManager;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @Permission(role = UserRole.ANONYMOUS, exception = ChatExceptionEnum.USER_ALREADY_LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public User login(@Valid @RequestBody AuthUserDto userDto, Errors validationErrors, HttpServletRequest httpServletRequest) {
        if (validationErrors.hasErrors()) {
            new ExceptionThrower(new BadRequestDataException()).addValidationsError(validationErrors).throwException();
        }
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.login(name, password, httpServletRequest.getRemoteAddr());
        onlineUser.setUser(user);
        onlineUserManager.addUser(user);
        return user;
    }

    @DeleteMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest httpServletRequest) {
        User logoutUser = onlineUser.getUser();
        authService.logout(logoutUser, httpServletRequest.getRemoteAddr());
        onlineUserManager.removeUser(logoutUser);
        httpServletRequest.getSession().invalidate();
    }

    @GetMapping
    public List<User> getOnlineUsers() {
        return onlineUserManager.getUsers();
    }


}
