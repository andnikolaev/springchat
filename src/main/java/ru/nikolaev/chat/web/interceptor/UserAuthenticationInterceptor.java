package ru.nikolaev.chat.web.interceptor;

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

@Component
public class UserAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private OnlineUser onlineUser;

    @Autowired
    private OnlineUserManager onlineUserManager;

    private EventService eventService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User currentUser = onlineUser.getUser();
        if (currentUser.getId() != 0) {
            UserStatus currentUserStatus = currentUser.getUserStatus();

            if (UserStatus.BANNED.equals(currentUserStatus)) {
                request.getSession().invalidate();
                throw new UserBannedException();
            }

            if (EventType.KICKED.equals(eventService.getLastEventForUser(currentUser).getEventType())) {
                request.getSession().invalidate();
                throw new UserKickedException();
            }
        }

        return true;
    }
}
