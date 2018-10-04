package ru.nikolaev.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikolaev.chat.annotation.Permission;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.entity.UserRole;
import ru.nikolaev.chat.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
public class TestController {


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

    @Permission(role = {UserRole.USER, UserRole.ADMIN})
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
