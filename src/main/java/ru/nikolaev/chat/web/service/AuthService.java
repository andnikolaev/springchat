package ru.nikolaev.chat.web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.exception.ExceptionThrower;
import ru.nikolaev.chat.exception.UserAlreadyExistException;
import ru.nikolaev.chat.exception.UserLoginFailedException;

@Service
public class AuthService {
    @Autowired
    private EventService eventService;
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
        eventService.sendEvent(user, EventType.REGISTERED, ip);
        return user;
    }

    public User login(String name, String password, String ip) {
        User user = userDao.checkAuth(name, password);
        if (user == null) {
            new ExceptionThrower(new UserLoginFailedException()).throwException();
        }
        eventService.sendEvent(user, EventType.LOGIN, ip);
        return user;
    }

    public void logout(User user, String ip) {
        eventService.sendEvent(user, EventType.LOGOUT, ip);
    }
}
