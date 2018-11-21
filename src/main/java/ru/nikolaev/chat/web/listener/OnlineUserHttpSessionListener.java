package ru.nikolaev.chat.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.web.storage.OnlineUser;
import ru.nikolaev.chat.web.storage.OnlineUserManager;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Enumeration;

@Slf4j
@Component
public class OnlineUserHttpSessionListener implements HttpSessionListener, ApplicationContextAware {

    @Autowired
    private OnlineUserManager onlineUserManager;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("Start sessionDestroyed");
        HttpSession session = httpSessionEvent.getSession();
        Enumeration<String> sessionsAttribute = session.getAttributeNames();
        while (sessionsAttribute.hasMoreElements()) {
            Object object = session.getAttribute(sessionsAttribute.nextElement());
            if (object instanceof OnlineUser) {
                OnlineUser onlineUser = (OnlineUser) object;
                User user = onlineUser.getUser();
                onlineUserManager.removeUser(user);
                log.info("Session destroyed for user" + user);
            }
        }
        log.info("End sessionDestroyed");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ((WebApplicationContext) applicationContext).getServletContext().addListener(this);
    }
}
