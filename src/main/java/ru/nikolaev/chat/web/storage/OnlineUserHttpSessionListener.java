package ru.nikolaev.chat.web.storage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.nikolaev.chat.entity.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@Component
public class OnlineUserHttpSessionListener implements HttpSessionListener, ApplicationContextAware {

    @Autowired
    private OnlineUserManager onlineUserManager;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        Enumeration<String> sessionsAttribute = session.getAttributeNames();
        while (sessionsAttribute.hasMoreElements()) {
            Object object = session.getAttribute(sessionsAttribute.nextElement());
            if (object instanceof OnlineUser) {
                OnlineUser onlineUser = (OnlineUser) object;
                User user = onlineUser.getUser();
                onlineUserManager.removeUser(user);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ((WebApplicationContext) applicationContext).getServletContext().addListener(this);
    }
}
