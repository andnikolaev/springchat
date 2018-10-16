package ru.nikolaev.chat.web;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.nikolaev.chat.entity.User;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSession {

    @Autowired
    private UserSessionStorageHandler userSessionStorageHandler;

    @Autowired
    private User user;

    private HttpSession httpSession;

    //TODO попробовать через листенер и убрать инжект хранилища отсюда
    @PreDestroy
    private void removeFromSessionStorage() {
        userSessionStorageHandler.remove(this);
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSession that = (UserSession) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(httpSession, that.httpSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, httpSession);
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "user=" + user +
                ", httpSession=" + httpSession +
                '}';
    }
}
