package ru.nikolaev.chat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikolaev.chat.enums.EventType;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class Event {
    private long id;
    private User owner;
    private User assignee;
    private EventType eventType;
    private String message;
    private String ip;
    private Timestamp timestamp;
}
