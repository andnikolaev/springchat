package ru.nikolaev.chat.entity;

public enum UserStatus {
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
}
