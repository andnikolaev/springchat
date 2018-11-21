package ru.nikolaev.chat.web.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class UserStatusDto {
    int statusId;
}
