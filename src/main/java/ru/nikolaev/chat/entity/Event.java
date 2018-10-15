package ru.nikolaev.chat.entity;

import ru.nikolaev.chat.enums.EventType;

public class Event {
    private long id;
    private long ownerId;
    private long assigneId;
    private EventType eventType;
    private String message;
    private String ip;
}
