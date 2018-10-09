package ru.nikolaev.chat.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.nikolaev.chat.entity.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@ApplicationScope
@Data
@AllArgsConstructor
public class UserSessionHandler {
    private Map<User, HttpSession> userMap;

    private UserSessionHandler() {
        this.userMap = new HashMap<>();
    }

    public void addSession(User user, HttpSession session) {
        userMap.put(user, session);
    }

    public Set<User> getOnlineUsers() {
        return userMap.keySet();
    }

    public void invalidateSessions(User user) {
        userMap.get(user).invalidate();
    }
}
