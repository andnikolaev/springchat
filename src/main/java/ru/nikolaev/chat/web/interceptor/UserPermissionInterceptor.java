package ru.nikolaev.chat.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.exception.ChatException;
import ru.nikolaev.chat.exception.UserBannedException;
import ru.nikolaev.chat.exception.UserKickedException;
import ru.nikolaev.chat.web.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserPermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private User onlineUser;

    @Autowired
    private EventService eventService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.debug("Start preHandle");
        checkUserStatus(request);
        boolean result = isUserHasPermissionForMethod(handler);
        log.debug("End preHandle, result  = {} ", result);
        return result;
    }

    private void checkUserStatus(HttpServletRequest request) {
        User currentUser = onlineUser;
        if (isUserOnline(currentUser)) {
            UserStatus currentUserStatus = currentUser.getUserStatus();

            if (UserStatus.BANNED.equals(currentUserStatus)) {
                request.getSession().invalidate();
                log.debug("Invalidating session for  {}", currentUser);
                log.warn("Throwing UserBannedException");
                throw new UserBannedException();
            }

            if (EventType.KICKED.equals(eventService.getLastEventForUser(currentUser).getEventType())) {
                request.getSession().invalidate();
                log.debug("Invalidating session for {} ", currentUser);
                log.warn("Throwing UserKickedException");
                throw new UserKickedException();
            }
        }
    }

    private boolean isUserOnline(User currentUser) {
        return currentUser.getId() != 0;
    }

    private boolean isUserHasPermissionForMethod(Object handler) {
        boolean result = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (isAnnotationPermissionPresent(handlerMethod)) {
                Permission methodPermissionAnnotation = handlerMethod.getMethodAnnotation(Permission.class);
                result = checkPermission(methodPermissionAnnotation);
                if (!result) {
                    ChatException exception = methodPermissionAnnotation.exception().getChatException();
                    log.error("User hasn`t access, throwing ");
                    throw exception;
                }
            } else {
                result = true;
            }
        }
        return result;
    }

    private boolean checkPermission(Permission methodPermissionAnnotation) {
        boolean result = false;
        log.debug("Permission annotation = {}", methodPermissionAnnotation);
        for (UserRole userRole : methodPermissionAnnotation.role()) {
            if (userRole.equals(onlineUser.getUserRole())) {
                log.debug("User has access");
                result = true;
            }
        }

        return result;
    }

    private boolean isAnnotationPermissionPresent(HandlerMethod handlerMethod) {
        return handlerMethod.getMethod().isAnnotationPresent(Permission.class);
    }
}
