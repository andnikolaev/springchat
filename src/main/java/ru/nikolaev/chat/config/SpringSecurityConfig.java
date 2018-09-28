package ru.nikolaev.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import ru.nikolaev.chat.web.security.MyBasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@ComponentScan(value = "ru.nikolaev.chat.web.security")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password("user1Pass")
                .authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement()
//                .maximumSessions(100)
//                .maxSessionsPreventsLogin(false)
//                .expiredUrl("/api/needLogin")
//                .sessionRegistry(sessionRegistry());

        http.authorizeRequests()
                .antMatchers("/all")
                .permitAll()
                .and()
                .httpBasic().authenticationEntryPoint(authenticationEntryPoint);
    }



    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
