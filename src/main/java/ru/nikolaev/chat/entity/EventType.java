package ru.nikolaev.chat.entity;

public enum EventType {

    REGISTERED(1),

    MESSAGE(2),

    LOGOUT(3),

    LOGIN(4),

    KICKED(5),

    BANNED(6),

    DELETED(7);

    private final int id;

    EventType(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }
}
