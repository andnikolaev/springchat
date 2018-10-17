package ru.nikolaev.chat.web.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component(value = "storage")
@ApplicationScope
public class OnlineUserSessionStorage {
    private Map<OnlineUser, List<HttpSession>> onlineUsersMap;

    public OnlineUserSessionStorage() {
        onlineUsersMap = new HashMap<>();
    }

    public void initUserSession(OnlineUser onlineUser, HttpSession httpSession) {
        List<HttpSession> httpSessionList = onlineUsersMap.get(onlineUser);
        if (httpSessionList == null) {
            httpSessionList = new LinkedList<>();
        }
        httpSessionList.add(httpSession);
        onlineUsersMap.put(onlineUser, httpSessionList);
    }

    public void invalidateAllSessions(OnlineUser onlineUser) {
        List<HttpSession> httpSessionList = onlineUsersMap.get(onlineUser);
        if (httpSessionList != null && httpSessionList.size() > 0) {
            httpSessionList.forEach(HttpSession::invalidate);
        }
        removeOnlineUser(onlineUser);
    }

    public void invalidateUserSession(HttpSession httpSession) {
        httpSession.invalidate();
    }

    public void removeUserSession(HttpSession httpSession) {
        AtomicReference<OnlineUser> currentOnlineUser = new AtomicReference<>();
        onlineUsersMap.forEach((key, value) -> {
            currentOnlineUser.set(key);
            value.remove(httpSession);
        });
        if (currentOnlineUser.get() != null && onlineUsersMap.get(currentOnlineUser.get()).size() == 0) {
            removeOnlineUser(currentOnlineUser.get());
        }
    }

    public List<OnlineUser> getOnlineUsers() {
        return new ArrayList<>(onlineUsersMap.keySet());
    }

    private void removeOnlineUser(OnlineUser onlineUser) {
        onlineUsersMap.remove(onlineUser);
    }
}
