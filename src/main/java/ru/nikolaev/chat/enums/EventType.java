package ru.nikolaev.chat.enums;

public enum EventType {

    UNKNOWN(0),

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

    public static EventType getEventTypeById(int id) {
        for (EventType eventType : values()) {
            if (eventType.id == id) {
                return eventType;
            }
        }
        return UNKNOWN;
    }
}
