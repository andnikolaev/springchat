package ru.nikolaev.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nikolaev.chat.dao.EventDao;

public class AdminService {
    @Autowired
    private EventDao eventDao;

}