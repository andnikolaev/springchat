package ru.nikolaev.chat.enums;

public enum UserStatus {
    UNKNOWN(0),
    ACTIVE(1),
    BANNED(2),
    DELETED(3);
    private final int id;

    UserStatus(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public static UserStatus getEventTypeById(int id) {
        for (UserStatus userStatus : values()) {
            if (userStatus.id == id) {
                return userStatus;
            }
        }
        return UNKNOWN;
    }
}
