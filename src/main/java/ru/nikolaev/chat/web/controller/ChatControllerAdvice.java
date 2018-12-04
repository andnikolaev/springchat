package ru.nikolaev.chat.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nikolaev.chat.exception.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ChatControllerAdvice {

    @ExceptionHandler({BadRequestDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<String> processExceptionHandler(BadRequestDataException ex) {
        log.info("Handling BadRequestDataException");
        log.trace("BadRequestDataException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({UserAlreadyLoginException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserAlreadyLoginException ex) {
        log.info("Handling UserAlreadyLoginException");
        ex.addError("youAlreadyLogin");
        log.trace("UserAlreadyLoginException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserAlreadyExistException ex) {
        log.info("Handling UserAlreadyExistException");
        ex.addError("userExist");
        log.trace("UserAlreadyExistException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({UserLoginFailedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserLoginFailedException ex) {
        log.info("Handling UserLoginFailedException");
        ex.addError("userNotFound");
        log.trace("UserLoginFailedException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({UserKickedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserKickedException ex) {
        log.info("Handling UserKickedException");
        ex.addError("userKicked");
        log.trace("UserKickedException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({UserBannedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserBannedException ex) {
        log.info("Handling UserBannedException");
        ex.addError("userBanned");
        log.trace("UserBannedException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    List<String> processExceptionHandler(AccessDeniedException ex) {
        log.info("Handling AccessDeniedException");
        log.trace("AccessDeniedException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({DataBaseAccessFailedException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    List<String> processExceptionHandler(DataBaseAccessFailedException ex) {
        log.info("Handling DataBaseAccessFailedException");
        log.trace("DataBaseAccessFailedException", ex);
        return ex.getErrorList();
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    void processExceptionHandler(NoSuchElementException ex) {
        log.info("Handling NoSuchElementException");
        log.trace("NoSuchElementException", ex);
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    void processExceptionHandler(UserNotFoundException ex) {
        log.info("Handling UserNotFoundException");
        log.trace("UserNotFoundException", ex);
    }
}
