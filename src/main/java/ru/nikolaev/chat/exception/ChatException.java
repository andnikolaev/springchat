package ru.nikolaev.chat.exception;

import java.util.LinkedList;
import java.util.List;

public class ChatException extends RuntimeException {

    private List<String> errors;

    public ChatException() {
        super();
        this.errors = new LinkedList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public List<String> getErrorList() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
