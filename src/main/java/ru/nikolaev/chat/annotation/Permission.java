package ru.nikolaev.chat.annotation;

import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.ChatExceptionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Permission {
    UserRole[] role() default UserRole.USER;

    ChatExceptionEnum exception() default ChatExceptionEnum.ACCESS_DENIED;
}
