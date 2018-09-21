package ru.nikolaev.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Locale;

@Slf4j
@Configuration
@ComponentScan("ru.nikolaev.chat.dao")
@PropertySource("classpath:datasource-cfg.properties")
public class RepositoryConfig {
    // The Environment class serves as the property holder
    // and stores all the properties loaded by the @PropertySource
    @Autowired
    private Environment env;

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        Locale.setDefault(Locale.ENGLISH);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // See: datasouce-cfg.properties
        dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
        dataSource.setUrl(env.getProperty("ds.url"));
        dataSource.setUsername(env.getProperty("ds.username"));
        dataSource.setPassword(env.getProperty("ds.password"));

        log.info(String.format("## getDataSource: %s", dataSource));

        return dataSource;
    }

    @Bean
    @Autowired
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
