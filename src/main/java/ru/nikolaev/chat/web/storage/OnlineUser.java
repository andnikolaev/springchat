package ru.nikolaev.chat.web.storage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@EqualsAndHashCode(of = "id")
public class OnlineUser {
    private long id;
    private String name;
}
