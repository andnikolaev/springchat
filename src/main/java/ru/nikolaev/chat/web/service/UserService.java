package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.User;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;



    public boolean register(User user) {

        return false;
    }

}
