package ru.nikolaev.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import ru.nikolaev.chat.web.interceptor.UserAuthenticationInterceptor;
import ru.nikolaev.chat.web.interceptor.UserPermissionInterceptor;

import java.util.Locale;

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
        registry.addViewController("/").setViewName("home");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserPermissionInterceptor());
        registry.addInterceptor(new UserAuthenticationInterceptor());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

}
