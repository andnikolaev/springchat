package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.User;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventService eventService;


    public User getUserWithActualData(User user) {
        return userDao.getUserById(user.getId());
    }
}
