package ru.nikolaev.chat.web.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.BadRequestDataException;
import ru.nikolaev.chat.exception.ChatExceptionEnum;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.utility.ModelMapperToDto;
import ru.nikolaev.chat.web.dto.AuthUserDto;
import ru.nikolaev.chat.web.dto.UserDto;
import ru.nikolaev.chat.web.service.AuthService;
import ru.nikolaev.chat.web.storage.OnlineUserManager;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/sessions")
public class SessionController {
    @Autowired
    private User onlineUser;

    @Autowired
    private OnlineUserManager onlineUserManager;
    @Autowired
    private AuthService authService;

    @Autowired
    private ModelMapperToDto modelMapperToDto;

    @PostMapping
    @Permission(role = UserRole.ANONYMOUS, exception = ChatExceptionEnum.USER_ALREADY_LOGIN)
    @ResponseStatus(HttpStatus.OK)
    public UserDto login(@Valid @RequestBody AuthUserDto userDto, Errors validationErrors, HttpServletRequest httpServletRequest) {
        log.info("Start login User = {}", userDto);
        if (validationErrors.hasErrors()) {
            log.warn("Throwing new exception BadRequestDataException with validators error");
            log.debug("Validators error = {}", validationErrors);
            new ExceptionThrower(new BadRequestDataException()).addValidationsError(validationErrors).throwException();
        }
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.login(name, password, httpServletRequest.getRemoteAddr());
        BeanUtils.copyProperties(user, onlineUser);
        onlineUserManager.addUser(user);
        UserDto resultUserDto = modelMapperToDto.convertToUserDto(user);
        log.info("End login user = {}", resultUserDto);
        return resultUserDto;
    }

    @DeleteMapping
    @Permission(role = {UserRole.ADMIN, UserRole.USER})
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest httpServletRequest) {
        User logoutUser = onlineUser;
        log.info("Start logout user = {}", logoutUser);
        authService.logout(logoutUser, httpServletRequest.getRemoteAddr());
        onlineUserManager.removeUser(logoutUser);
        httpServletRequest.getSession().invalidate();
        log.info("End logout user = {}", logoutUser);
    }

    @GetMapping
    public List<UserDto> getOnlineUsers() {
        return onlineUserManager.getUsers().stream().map(modelMapperToDto::convertToUserDto).collect(Collectors.toList());
    }


}
