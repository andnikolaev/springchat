package ru.nikolaev.chat.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UpdateCurrentOnlineUserInterceptor implements HandlerInterceptor {

    @Autowired
    private User onlineUser;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("Start preHandle");
        if (onlineUser.getId() != 0) {
            User actualUser = userService.getUserWithActualData(onlineUser);
            onlineUser = actualUser;
            log.debug("Current user " + actualUser + " Actual user" + actualUser);
        } else {
            log.debug("Online user doest`t exist");
        }
        log.debug("end preHandle");
        return true;
    }
}
