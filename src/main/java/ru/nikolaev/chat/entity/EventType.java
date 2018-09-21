package ru.nikolaev.chat.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EventType {

    private int id;

    private String name;

}
