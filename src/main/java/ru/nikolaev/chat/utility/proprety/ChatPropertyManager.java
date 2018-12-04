package ru.nikolaev.chat.utility.proprety;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@PropertySource("classpath:chat.properties")
public class ChatPropertyManager implements PropertyManager {

    @Autowired
    private Environment environment;

    @Override
    public String getProperty(ChatPropertiesEnum chatPropertiesEnum) {
        return environment.getProperty(chatPropertiesEnum.getKey(), chatPropertiesEnum.getDefaultValue());
    }
}
