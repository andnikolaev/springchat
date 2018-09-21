package ru.nikolaev.chat.dao;

import ru.nikolaev.chat.entity.Message;

import java.util.List;

public interface MessageDao {

    List<Message> getLastMessages(int count);

    void sendMessage(Message message);

}
