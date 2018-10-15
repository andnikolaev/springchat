package ru.nikolaev.chat.entity;

public enum UserRole {
    UNKNOWN(0),
    ANONYMOUS(1),
    USER(2),
    ADMIN(3);
    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public static UserRole getEventTypeById(int id) {
        for (UserRole userRole : values()) {
            if (userRole.id == id) {
                return userRole;
            }
        }
        return UNKNOWN;
    }
}
