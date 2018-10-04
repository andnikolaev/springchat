package ru.nikolaev.chat.web;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.nikolaev.chat.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ApplicationScope
public class UserSessionHandler {
    Map<User, List<UserSession>> userMap = new HashMap<>();
}
