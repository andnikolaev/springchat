package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.Message;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addUser")
    public String test(HttpServletRequest request) {
        User user = new User();
        user.setName("ad=jud");
        user.setPassword("sdasd");
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        // user.setIp(remoteAddr);
        userService.register(user);
        return user.toString();
    }

    @PostMapping(value = "/sendMessage")
    public String sendMessage(@RequestBody User user, @RequestBody String message) {
        return "a";
    }

    @GetMapping(value = "/getUser")
    public String getUser(HttpSession session) {
        User user = (User) session.getAttribute("uset");
        if (user == null) {
            user = new User();
            user.setId(6);
            user.setName("and");
            session.setAttribute("uset", user);
        }
        return user.toString();
    }

    @PostMapping(value = "/allUsers")
    public String allUsers(@RequestBody User user) {
        return user.toString();
    }


}
