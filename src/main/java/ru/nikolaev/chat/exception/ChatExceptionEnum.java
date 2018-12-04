package ru.nikolaev.chat.exception;

public enum ChatExceptionEnum {
    ACCESS_DENIED(new AccessDeniedException()),
    USER_EXIST(new UserAlreadyExistException()),
    USER_ALREADY_LOGIN(new UserAlreadyLoginException());


    private ChatException chatException;

    ChatExceptionEnum(ChatException e) {
        chatException = e;
    }

    public ChatException getChatException() {
        return chatException;
    }
}
