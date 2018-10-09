package ru.nikolaev.chat.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.entity.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserPermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private User user;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(Permission.class)) {
                Permission methodPermissionAnnotation = handlerMethod.getMethodAnnotation(Permission.class);
                //TODO Refactor
                for (UserRole userRole : methodPermissionAnnotation.role()) {
                    if (userRole.equals(user.getUserRole())) {
                        result = true;
                    }
                }
//                Arrays.stream(methodPermissionAnnotation.role()).anyMatch(x -> x.equals(user.getUserRole()));
                System.out.println(methodPermissionAnnotation);
            } else {
                result = true;
            }
        }
        return result;
    }
}
