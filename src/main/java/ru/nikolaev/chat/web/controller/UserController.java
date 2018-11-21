package ru.nikolaev.chat.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.web.dto.AuthUserDto;
import ru.nikolaev.chat.web.dto.EventDto;
import ru.nikolaev.chat.web.dto.UserDto;
import ru.nikolaev.chat.web.dto.UserStatusDto;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.BadRequestDataException;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.utility.ModelMapperToDto;
import ru.nikolaev.chat.web.service.AdminService;
import ru.nikolaev.chat.web.service.AuthService;
import ru.nikolaev.chat.web.storage.OnlineUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
@Slf4j
@CrossOrigin
public class UserController {

    @Autowired
    private OnlineUser onlineUser;

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ModelMapperToDto modelMapperToDto;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Permission(role = UserRole.ANONYMOUS)
    public UserDto registerUser(@Valid @RequestBody AuthUserDto userDto, Errors validationErrors, HttpServletRequest httpServletRequest) {
        if (validationErrors.hasErrors()) {
            new ExceptionThrower(new BadRequestDataException()).addValidationsError(validationErrors).throwException();
        }
        String name = userDto.getName();
        String password = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        User user = authService.register(name, password, httpServletRequest.getRemoteAddr());
        return modelMapperToDto.convertToUserDto(user);
    }

    @Permission(role = UserRole.ADMIN)
    @DeleteMapping("/{id}/session")
    public EventDto kickUser(@PathVariable long id, HttpServletRequest httpServletRequest) {
        Event event = adminService.kickUser(onlineUser.getUser(), id, httpServletRequest.getRemoteAddr());
        return modelMapperToDto.convertToEventDto(event);
    }

    @Permission(role = UserRole.ADMIN)
    @PostMapping("/{id}/session")
    public UserDto updateUserStatus(@PathVariable long id, @RequestBody UserStatusDto userStatusDto, HttpServletRequest httpServletRequest) {
        User user = adminService.updateUserStatus(onlineUser.getUser(), id, userStatusDto.getStatusId(), httpServletRequest.getRemoteAddr());
        return modelMapperToDto.convertToUserDto(user);
    }

    @GetMapping("/session")
    @Permission(role = {UserRole.USER, UserRole.ADMIN})
    public UserDto getCurrentUser() {
        return modelMapperToDto.convertToUserDto(onlineUser.getUser());
    }

}
