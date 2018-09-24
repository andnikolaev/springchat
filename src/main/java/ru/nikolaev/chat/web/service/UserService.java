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

    public boolean sendMessage(User user, String message){
        eventDao.sendEvent(user, EventType.MESSAGE, message);
        return false;
    }

    public List<User> getOnlineUsers() {
        return new ArrayList<>();
    }
}
