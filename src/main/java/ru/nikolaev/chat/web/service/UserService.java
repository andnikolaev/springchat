package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.EventType;
import ru.nikolaev.chat.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDao eventDao;

    @Transactional
    public boolean register(User user) {
        user.setId(userDao.addUser(user));
        eventDao.sendEvent(user, EventType.REGISTERED, "User was register");
        return false;
    }

    public void login(String name, String password) {
        //    eventDao.sendEvent(user, EventType.LOGIN, "User login in this chat");
    }

    public void logout(User user) {
        eventDao.sendEvent(user, EventType.LOGIN, "User logout from this chat");
    }


    public List<User> getOnlineUsers() {
        return new ArrayList<>();
    }
}
