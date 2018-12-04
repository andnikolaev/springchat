package ru.nikolaev.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.nikolaev.chat.utility.proprety.ChatPropertiesEnum;
import ru.nikolaev.chat.utility.proprety.ChatPropertyManager;
import ru.nikolaev.chat.utility.proprety.PropertyManager;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Locale;

@Slf4j
@Configuration
@ComponentScan("ru.nikolaev.chat.dao")
public class RepositoryConfig {
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
    @Autowired
    public DataSource dataSource(PropertyManager chatPropertyManager) throws NamingException {
        Locale.setDefault(Locale.ENGLISH);
        return (DataSource) new JndiTemplate().lookup(chatPropertyManager.getProperty(ChatPropertiesEnum.DATA_SOURCE));
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

    @Bean
    public PropertyManager chatPropertyManager() {
        return new ChatPropertyManager();
    }
}
