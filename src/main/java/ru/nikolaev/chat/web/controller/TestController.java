package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.User;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserDao jdbcUserDao;

    @GetMapping("/addUser")
    public String test() {
        User user = new User();
        user.setName("adsd");
        user.setPassword("s");
        user.setId(jdbcUserDao.register(user));
        return user.toString();
    }
}
