package ru.nikolaev.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Component(value = "user")
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {

    private long id;
    private String name;

    private UserStatus userStatus;
    private UserRole userRole;

    private String ip;

    @PostConstruct
    public void initRole() {
        userRole = UserRole.ANONYMOUS;
    }
}
