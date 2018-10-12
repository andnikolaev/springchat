package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.User;

import java.util.List;

public interface UserDao {

    int addUser(User user);

    User login(User user);

    void logout(User user);

    User getUserByName(String name);

    List<User> getAllLoggedUsers();

}
