package ru.nikolaev.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Locale;

@Slf4j
@Configuration
@ComponentScan("ru.nikolaev.chat.dao")
@PropertySource("classpath:datasource-cfg.properties")
@EnableTransactionManagement
public class RepositoryConfig {
    @Autowired
    private Environment env;
//
//    @Bean(name = "dataSource")
//    public DataSource getDataSource() {
//        Locale.setDefault(Locale.ENGLISH);
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        // See: datasouce-cfg.properties
//        dataSource.setDriverClassName(env.getProperty("ds.database-driver"));
//        dataSource.setUrl(env.getProperty("ds.url"));
//        dataSource.setUsername(env.getProperty("ds.username"));
//        dataSource.setPassword(env.getProperty("ds.password"));
//
//        log.info(String.format("## getDataSource: %s", dataSource));
//
//        return dataSource;
//    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() throws NamingException {
        Locale.setDefault(Locale.ENGLISH);
        return (DataSource) new JndiTemplate().lookup(env.getProperty("jdbc.url"));
    }

    @Bean
    @Autowired
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "tran")
    @Autowired
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
