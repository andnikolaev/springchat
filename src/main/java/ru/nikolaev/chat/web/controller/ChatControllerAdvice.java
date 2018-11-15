package ru.nikolaev.chat.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nikolaev.chat.exception.*;

import java.util.List;

@RestControllerAdvice
public class ChatControllerAdvice {

    @ExceptionHandler({BadRequestDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<String> processExceptionHandler(BadRequestDataException ex) {
        return ex.getErrorList();
    }

    List<String> processExceptionHandler(UserAlreadyLoginException ex) {
        ex.addError("youAlreadyLogin");
        return ex.getErrorList();
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserAlreadyExistException ex) {
        ex.addError("userExist");
        return ex.getErrorList();
    }

    @ExceptionHandler({UserLoginFailedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserLoginFailedException ex) {
        ex.addError("userNotFound");
        return ex.getErrorList();
    }

    @ExceptionHandler({UserKickedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserKickedException ex) {
        ex.addError("userKicked");
        return ex.getErrorList();
    }

    @ExceptionHandler({UserBannedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserBannedException ex) {
        ex.addError("userBanned");
        return ex.getErrorList();
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    List<String> processExceptionHandler(AccessDeniedException ex) {
        return ex.getErrorList();
    }
}
