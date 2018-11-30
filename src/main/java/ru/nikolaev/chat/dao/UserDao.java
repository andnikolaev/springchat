package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.exception.FailedAddUserException;

import java.util.Optional;

public interface UserDao {

    User addUser(User user) throws FailedAddUserException;

    User updateUser(User user);

    User checkAuth(String name, String password);

    Optional<User> getUserByName(String name);

    Optional<User> getUserById(long id);

}
