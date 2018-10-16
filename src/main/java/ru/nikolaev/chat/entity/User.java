package ru.nikolaev.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;

import javax.annotation.PostConstruct;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    private long id;
    private String name;

    private UserStatus userStatus;
    private UserRole userRole;

    private String ip;

    public User(long id) {
        this.id = id;
    }

    @PostConstruct
    public void initRole() {
        userRole = UserRole.ANONYMOUS;
    }
}
