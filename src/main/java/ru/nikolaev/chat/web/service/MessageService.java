package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nikolaev.chat.dao.EventDao;
import ru.nikolaev.chat.entity.EventType;
import ru.nikolaev.chat.entity.Message;
import ru.nikolaev.chat.entity.User;

import java.util.ArrayList;
import java.util.List;

public class MessageService {
    @Autowired
    private EventDao eventDao;

    public void sendMessage(User user, String message) {
        eventDao.sendEvent(user, EventType.MESSAGE, message);
    }

    public List<Message> getLastMessages(int count) {
        List<Message> messageList = null;
        return messageList;
    }
}
