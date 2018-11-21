package ru.nikolaev.chat.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.*;
import ru.nikolaev.chat.entity.User;
import ru.nikolaev.chat.enums.UserRole;
import ru.nikolaev.chat.utility.ModelMapperToDto;
import ru.nikolaev.chat.web.interceptor.UpdateCurrentOnlineUserInterceptor;
import ru.nikolaev.chat.web.interceptor.UserAuthenticationInterceptor;
import ru.nikolaev.chat.web.interceptor.UserPermissionInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan({"ru.nikolaev.chat.web", "ru.nikolaev.chat.entity"})
public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login")
                .setViewName("forward:/index.html");
        registry.addViewController("/registration")
                .setViewName("forward:/index.html");
    }

    @Bean
    public UpdateCurrentOnlineUserInterceptor updateCurrentOnlineUserInterceptor() {
        return new UpdateCurrentOnlineUserInterceptor();
    }

    @Bean
    public UserPermissionInterceptor userPermissionInterceptor() {
        return new UserPermissionInterceptor();
    }

    @Bean
    public UserAuthenticationInterceptor userAuthenticationInterceptor() {
        return new UserAuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(updateCurrentOnlineUserInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(userPermissionInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(userAuthenticationInterceptor()).addPathPatterns("/api/**");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperToDto modelMapperToDto() {
        return new ModelMapperToDto();
    }

    @Bean
    @Scope("prototype")
    public User user() {
        User user = new User();
        user.setUserRole(UserRole.ANONYMOUS);
        return user;
    }

}
