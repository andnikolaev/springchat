package ru.nikolaev.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Component
public class User {

    private int id;

    private String name;

    private String password;

    private String ip;
}
