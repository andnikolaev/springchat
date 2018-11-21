package ru.nikolaev.chat.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.enums.UserStatus;

@Data
@NoArgsConstructor
@Component
public class UserDto {
    private long id;
    private String name;
    private UserStatus userStatus;
    private UserRole userRole;
}
