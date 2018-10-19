package ru.nikolaev.chat.web.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@ApplicationScope
public class OnlineUserManager {

    private Set<OnlineUser> onlineUserSet;

    public OnlineUserManager() {
        onlineUserSet = new HashSet<>();
    }

    public void addUser(OnlineUser onlineUser) {
        onlineUserSet.add(onlineUser);
    }

    public List<OnlineUser> getUsers() {
        return new ArrayList<>(onlineUserSet);
    }

    public void removeUser(OnlineUser onlineUser) {
        onlineUserSet.remove(onlineUser);
    }
}
