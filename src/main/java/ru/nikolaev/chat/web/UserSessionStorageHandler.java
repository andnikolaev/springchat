package ru.nikolaev.chat.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.nikolaev.chat.entity.User;

import javax.servlet.http.HttpSession;
import java.util.*;

@Component
@ApplicationScope
public class UserSessionStorageHandler {
    private Set<UserSession> userSessions;

    public UserSessionStorageHandler() {
        userSessions = new HashSet<>();
    }

    public void initUserSession(UserSession userSession, HttpSession httpSession) {
        userSession.setHttpSession(httpSession);
        userSessions.add(convertProxyToCommonUserSession(userSession));
    }

    public void invalidate(User invalidationUser) {
        for (UserSession userSession : userSessions) {
            if (invalidationUser.equals(userSession.getUser())) {
                userSession.getHttpSession().invalidate();
            }
        }
    }

    public void invalidate(UserSession userSession) {
        UserSession commonUserSession = convertProxyToCommonUserSession(userSession);
        Optional<UserSession> optionalUserSession = userSessions.stream().filter(commonUserSession::equals).findFirst();
        optionalUserSession.ifPresent(userAuth -> userAuth.getHttpSession().invalidate());
    }

    public List<User> getUsers() {
        Set<User> users = new HashSet<>();
        for (UserSession userSession : userSessions) {
            users.add(userSession.getUser());
        }
        return new ArrayList<>(users);
    }

    public void remove(UserSession userSession) {
        userSessions.remove(convertProxyToCommonUserSession(userSession));
    }

    private UserSession convertProxyToCommonUserSession(UserSession proxyUserSessionObject) {
        UserSession commonUserSessionObject = new UserSession();
        BeanUtils.copyProperties(proxyUserSessionObject, commonUserSessionObject);
        return commonUserSessionObject;
    }
}
