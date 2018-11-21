package ru.nikolaev.chat.web.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@RequestScope
@Data
public class AuthUserDto {
    @NotNull
    @Size(min = 3, max = 20)
    private String name;
    @NotNull
    @Size(min = 3, max = 20)
    private String password;
}
