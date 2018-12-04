package ru.nikolaev.chat.web.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.exception.*;

@Slf4j
@Service
@EnableTransactionManagement
public class AuthService {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserDao userDao;

    @Transactional(value = "tran", rollbackFor = ChatException.class)
    public User register(String name, String password, String ip) {
        if (userDao.getUserByName(name).isPresent()) {
            throw new UserAlreadyExistException();
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

    @Transactional(value = "tran")
    public User login(String name, String password, String ip) {
        User user;
        try {
            user = userDao.checkAuth(name, password);
        } catch (UserNotFoundException e) {
            log.warn("Throwing UserLoginFailedException", e);
            throw new UserLoginFailedException();
        }
        if (UserStatus.BANNED.equals(user.getUserStatus())) {
            throw new UserBannedException();
        }
        eventService.sendEvent(user, EventType.LOGIN, ip);
        return user;
    }

    public void logout(User user, String ip) {
        eventService.sendEvent(user, EventType.LOGOUT, ip);
    }
}
