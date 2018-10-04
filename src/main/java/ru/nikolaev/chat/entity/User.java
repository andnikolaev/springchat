package ru.nikolaev.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component(value = "user")
@SessionScope
public class User {
    private int id;
    private String name;
    @JsonIgnore
    private String password;
    private UserStatus userStatus;
    private UserRole userRole;
}
