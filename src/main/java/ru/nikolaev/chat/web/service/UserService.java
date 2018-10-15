package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

       @Autowired
    private EventDao eventDao;

    public boolean register(User user) {
        user.setId(userDao.addUser(user));
        eventDao.sendEvent(user, EventType.REGISTERED, "User was register");
        return false;
    }

    public List<User> getOnlineUsers() {
        return new ArrayList<>();
    }
}
