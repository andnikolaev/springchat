package ru.nikolaev.chat.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikolaev.chat.dao.UserDao;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.enums.UserStatus;
import ru.nikolaev.chat.exception.UserNotFoundException;

@Slf4j
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
        log.info("Start banUser() with id " + banedUserId + " from admin " + owner);
        User bannedUser = userDao.getUserById(banedUserId).orElseThrow(UserNotFoundException::new);
        bannedUser.setUserStatus(UserStatus.BANNED);
        User resultUser = userDao.updateUser(bannedUser);

        eventService.sendEvent(owner, new User(banedUserId), EventType.BANNED, ownerIp);

        log.info("End banUser() user " + resultUser);
        return resultUser;
    }

    public User deleteUser(User owner, long deletedUserId, String ownerIp) {
        log.info("Start deleteUser() with id " + deletedUserId + " from admin " + owner);
        User bannedUser = userDao.getUserById(deletedUserId).orElseThrow(UserNotFoundException::new);
        bannedUser.setUserStatus(UserStatus.DELETED);
        User resultDeletedUser = userDao.updateUser(owner);

        eventService.sendEvent(owner, new User(deletedUserId), EventType.DELETED, ownerIp);
        log.info("End banUser() user " + resultDeletedUser);
        return resultDeletedUser;
    }

    public User updateUserStatus(User user, long id, int statusId, String ownerIp) {
        log.info("Start updateUserStatus() with id " + id + " from admin " + user);
        User updatedUser = null;
        UserStatus userStatus = UserStatus.getUserStatusById(statusId);
        if (UserStatus.BANNED.equals(userStatus)) {
            log.info("Ban user");
            updatedUser = banUser(user, id, ownerIp);
        } else if (UserStatus.DELETED.equals(userStatus)) {
            log.info("Delete user");
            updatedUser = deleteUser(user, id, ownerIp);
        }
        log.info("End updateUserStatus() user " + updatedUser);
        return updatedUser;
    }
}
