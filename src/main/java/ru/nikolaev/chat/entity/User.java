package ru.nikolaev.chat.entity;

import lombok.*;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User {

    private long id;
    private String name;
    private String password;
    private UserStatus userStatus;
    private UserRole userRole;
    private String ip;

    public User(long id) {
        this.id = id;
    }

}
