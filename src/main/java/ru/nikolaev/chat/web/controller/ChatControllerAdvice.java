package ru.nikolaev.chat.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import ru.nikolaev.chat.exception.BadRequestDataException;
import ru.nikolaev.chat.exception.UserAlreadyExistException;

import java.util.List;

@RestControllerAdvice
public class ChatControllerAdvice {

    @ExceptionHandler({BadRequestDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<String> processExceptionHandler(BadRequestDataException ex) {
        return ex.getErrorList();
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    List<String> processExceptionHandler(UserAlreadyExistException ex) {
        ex.addError("userExist");
        return ex.getErrorList();
    }
}
