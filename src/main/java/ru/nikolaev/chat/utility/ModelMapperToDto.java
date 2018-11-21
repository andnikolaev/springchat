package ru.nikolaev.chat.utility;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nikolaev.chat.entity.Event;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.web.dto.EventDto;
import ru.nikolaev.chat.web.dto.UserDto;

public class ModelMapperToDto {
    @Autowired
    private ModelMapper modelMapper;

    public UserDto convertToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public EventDto convertToEventDto(Event event) {
        EventDto eventDto = modelMapper.map(event, EventDto.class);
        return eventDto;
    }
}
