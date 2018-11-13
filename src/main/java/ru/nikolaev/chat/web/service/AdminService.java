package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserStatus;

@Service
public class AdminService {
    @Autowired
    private EventDao eventDao;


    public void kickUser(long ownerId, long kickedUserId, String ownerIp) {
       // eventDao.sendEvent(new User(ownerId), EventType.KICKED, "User was kicked from this chat", ownerIp, new User(kickedUserId));
    }

    public void banUser(long ownerId, long banedUserId, String ownerIp) {
        updateUserStatus(ownerId, banedUserId, EventType.BANNED, UserStatus.BANNED, ownerIp);
    }

    public void deleteUser(long ownerId, long banedUserId, String ownerIp) {
        updateUserStatus(ownerId, banedUserId, EventType.DELETED, UserStatus.DELETED, ownerIp);
    }

    public void updateUserStatus(long adminId, long userId, EventType eventType, UserStatus userStatus, String adminIp) {
      //  eventDao.sendEvent(new User(adminId), eventType, "User was kicked from this chat", adminIp, new User(userId));
    }
}
