package ru.nikolaev.chat.web.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.nikolaev.chat.entity.User;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@EqualsAndHashCode(of = "user")
public class OnlineUser {

    @Autowired
    private User user;

}
