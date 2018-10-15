package ru.nikolaev.chat.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;

@Service
public class AuthService {
    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;

    public User register(String name, String password, String ip) {
        long userId = userDao.addUser(name, password);
        User user = new User(userId, name, UserStatus.ACTIVE, UserRole.USER, ip);
        eventDao.sendEvent(user, EventType.REGISTERED, "User registered in this chat");
        return user;
    }

    public User login(String name, String password, String ip) {
        User user = userDao.checkAuth(name, password);
        eventDao.sendEvent(user, EventType.LOGIN, "User login in this chat");
        return user;
    }

    public void logout(User user) {
        eventDao.sendEvent(user, EventType.LOGOUT, "User logout from this chat");
    }
}
