package ru.nikolaev.chat.web.storage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;

import javax.annotation.PostConstruct;

@Component
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OnlineUser {

    private long id;
    private String name;

    private UserStatus userStatus;
    private UserRole userRole;

    @JsonIgnore
    private String ip;

    public OnlineUser(long id) {
        this.id = id;
    }

    @PostConstruct
    public void initRole() {
        userRole = UserRole.ANONYMOUS;
    }
}
