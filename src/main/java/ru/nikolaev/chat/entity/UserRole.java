package ru.nikolaev.chat.entity;

public enum UserRole {
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
}
