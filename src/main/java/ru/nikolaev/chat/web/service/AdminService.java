package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserStatus;

@Service
public class AdminService {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserDao userDao;


    public Event kickUser(User owner, long kickedUserId, String ownerIp) {
        return eventService.sendEvent(owner, new User(kickedUserId), EventType.KICKED, ownerIp);
    }

    public User banUser(User owner, long banedUserId, String ownerIp) {
        User bannedUser = userDao.getUserById(banedUserId);
        bannedUser.setUserStatus(UserStatus.BANNED);
        userDao.updateUser(bannedUser);
        eventService.sendEvent(owner, new User(banedUserId), EventType.BANNED, ownerIp);
        return userDao.getUserById(banedUserId);
    }

    public User deleteUser(User owner, long deletedUserId, String ownerIp) {
        User bannedUser = userDao.getUserById(deletedUserId);
        bannedUser.setUserStatus(UserStatus.DELETED);
        userDao.updateUser(owner);
        eventService.sendEvent(owner, new User(deletedUserId), EventType.DELETED, ownerIp);
        return userDao.getUserById(deletedUserId);
    }

    public User updateUserStatus(User user, long id, int statusId, String ownerIp) {
        User updatedUser = null;
        UserStatus userStatus = UserStatus.getUserStatusById(statusId);
        if (UserStatus.BANNED.equals(userStatus)) {
            updatedUser = banUser(user, id, ownerIp);
        } else if (UserStatus.DELETED.equals(userStatus)) {
            updatedUser = deleteUser(user, id, ownerIp);
        }

        return updatedUser;
    }
}
