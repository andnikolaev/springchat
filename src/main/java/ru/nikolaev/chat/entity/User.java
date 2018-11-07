package ru.nikolaev.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;

import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
@EqualsAndHashCode(of = "id")
public class User {

    private long id;
    private String name;
    @JsonIgnore
    private String password;
    private UserStatus userStatus;
    private UserRole userRole;
    @JsonIgnore
    private String ip;

    public User(long id) {
        this.id = id;
    }

}
