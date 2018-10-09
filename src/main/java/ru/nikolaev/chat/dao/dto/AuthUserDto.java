package ru.nikolaev.chat.dao.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class AuthUserDto {
    private String name;
    private String password;
}
