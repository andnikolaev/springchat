package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.enums.EventType;
import ru.nikolaev.chat.entity.Message;
import ru.nikolaev.chat.entity.User;

import java.util.List;

public class MessageService {
    @Autowired
    private EventDao eventDao;

    public void sendMessage(User user, String message, String ip) {
        eventDao.sendEvent(user, EventType.MESSAGE, message, ip);
    }

    public List<Message> getLastMessages(int count) {
        List<Message> messageList = null;
        return messageList;
    }
}
