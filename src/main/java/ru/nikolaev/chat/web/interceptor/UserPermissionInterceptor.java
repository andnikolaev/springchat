package ru.nikolaev.chat.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.exception.ChatException;
import ru.nikolaev.chat.web.storage.OnlineUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class UserPermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private OnlineUser onlineUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("Start preHandle");
        boolean result = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(Permission.class)) {
                Permission methodPermissionAnnotation = handlerMethod.getMethodAnnotation(Permission.class);
                log.debug("Permission annotation " + methodPermissionAnnotation);
                for (UserRole userRole : methodPermissionAnnotation.role()) {
                    if (userRole.equals(onlineUser.getUser().getUserRole())) {
                        log.debug("User has access");
                        result = true;
                    }
                }
//                Arrays.stream(methodPermissionAnnotation.role()).anyMatch(x -> x.equals(user.getUserRole()));
                if (!result) {
                    ChatException exception = methodPermissionAnnotation.exception().getChatException();
                    log.warn("User hasn`t access, throwing " + exception.getClass().toString());
                    throw exception;
                }
            } else {
                result = true;
            }
        }
        log.debug("End preHandle, result + " + result);
        return result;
    }
}
