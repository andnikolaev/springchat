package ru.nikolaev.chat.exception;

import org.springframework.validation.Errors;

public class ExceptionThrower {

    private static final String ERROR_TEMPLATE = "%s error";

    private ChatException chatException;

    public ExceptionThrower(ChatException chatException) {
        this.chatException = chatException;
    }

    public ExceptionThrower addError(String error) {
        chatException.addError(error);
        return this;
    }

    public ExceptionThrower addValidationsError(Errors validationErrors) {
        validationErrors.getFieldErrors().forEach(fieldError -> chatException.addError(String.format(ERROR_TEMPLATE, fieldError.getField())));
        return this;
    }

    public void throwException() {
        throw chatException;
    }

}
