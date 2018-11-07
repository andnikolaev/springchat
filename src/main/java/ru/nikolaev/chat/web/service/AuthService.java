package ru.nikolaev.chat.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.exception.UserAlreadyExistException;

@Service
public class AuthService {
    @Autowired
    private EventDao eventDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public User register(String name, String password, String ip) {
        User existingUser = userDao.getUserByName(name);
        if (existingUser != null) {
            new ExceptionThrower(new UserAlreadyExistException()).throwException();
        }
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setUserRole(UserRole.USER);
        user.setUserStatus(UserStatus.ACTIVE);
        user = userDao.addUser(user);
        eventDao.sendEvent(user, EventType.REGISTERED, "New user registered in this chat", ip);
        return user;
    }

    public User login(String name, String password, String ip) {
        User user = userDao.checkAuth(name, password);
        eventDao.sendEvent(user, EventType.LOGIN, "User login in this chat", ip);
        return user;
    }

    public void logout(User user, String ip) {
        eventDao.sendEvent(user, EventType.LOGOUT, "User logout from this chat", ip);
    }
}
