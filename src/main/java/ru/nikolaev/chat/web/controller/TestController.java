package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.Message;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.web.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/addUser")
    public String test(HttpServletRequest request) {
        User user = new User();
        user.setName("ad=jud");
        user.setPassword("s");
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        user.setIp(remoteAddr);
        userService.register(user);
        return user.toString();
    }

    @PostMapping(value = "/sendMessage")
    public String sendMessage(@RequestBody User user, @RequestBody String message) {
        return "a";
    }
}
