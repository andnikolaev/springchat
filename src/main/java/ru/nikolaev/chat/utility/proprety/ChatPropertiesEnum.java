package ru.nikolaev.chat.utility.proprety;

public enum ChatPropertiesEnum {
    DATA_SOURCE("chat.jdbc.url", "java:comp/env/jdbc/chatDB"),
    LAST_EVENTS_COUNT("chat.last.events.count", "50"),
    LAST_MESSAGES_COUNT("chat.last.messages.count", "50");

    private String key;
    private String defaultValue;

    ChatPropertiesEnum(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
