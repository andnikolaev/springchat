package ru.nikolaev.chat.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Component
public class EventDto {
    private long id;
    private User owner;
    private User assignee;
    private EventType eventType;
    private String message;
    private Timestamp timestamp;
}
