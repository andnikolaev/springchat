package ru.nikolaev.chat.web.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.nikolaev.chat.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@ApplicationScope
public class OnlineUserManager {

    private Set<User> onlineUserSet;

    public OnlineUserManager() {
        onlineUserSet = new HashSet<>();
    }

    public void addUser(User onlineUser) {
        onlineUserSet.add(onlineUser);
    }

    public List<User> getUsers() {
        return new ArrayList<>(onlineUserSet);
    }

    public void removeUser(User onlineUser) {
        onlineUserSet.remove(onlineUser);
    }
}
