package ru.nikolaev.chat.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.exception.UserBannedException;
import ru.nikolaev.chat.exception.UserKickedException;
import ru.nikolaev.chat.web.service.EventService;
import ru.nikolaev.chat.web.storage.OnlineUser;
import ru.nikolaev.chat.web.storage.OnlineUserManager;

import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private OnlineUser onlineUser;

    @Autowired
    private EventService eventService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("Start preHandle");
        User currentUser = onlineUser.getUser();
        if (currentUser.getId() != 0) {
            UserStatus currentUserStatus = currentUser.getUserStatus();

            if (UserStatus.BANNED.equals(currentUserStatus)) {
                request.getSession().invalidate();
                log.debug("Invalidating session for " + currentUser);
                log.warn("Throwing UserBannedException");
                throw new UserBannedException();
            }

            if (EventType.KICKED.equals(eventService.getLastEventForUser(currentUser).getEventType())) {
                request.getSession().invalidate();
                log.debug("Invalidating session for " + currentUser);
                log.warn("Throwing UserKickedException");
                throw new UserKickedException();
            }
        }
        log.debug("end preHandle");
        return true;
    }
}
