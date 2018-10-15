package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserStatus;

public class AdminService {
    @Autowired
    private EventDao eventDao;

    public User updateUserStatus(long adminId, long userId, UserStatus userStatus, String adminIp) {
        return null;
    }

}
