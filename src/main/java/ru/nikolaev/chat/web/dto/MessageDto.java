package ru.nikolaev.chat.web.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.constraints.Size;

@Component
@RequestScope
@Data
public class MessageDto {
    @Size(min = 1, max = 255)
    private String text;
}
