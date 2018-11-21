package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.User;

public interface UserDao {

    User addUser(User user);

    User updateUser(User user);

    User checkAuth(String name, String password);

    User getUserByName(String name);

    User getUserById(long id);

}
