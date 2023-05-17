package spring.module.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("jdbc")
public class DataSourceFactory {

    @Value("${jdbc.url:localhost:3306}")
    private String jdbcUrl;
    @Value("${jdbc.user:sdim2087}")
    private String jdbcUser;
    @Value("${jdbc.passwd:1234}")
    private String passwd;
    @Value("${jdbc.poolSize:10}")
    private Integer connectionNumber;
    @Value("${jdbc.driver:com.mysql.cj.jdbc.Driver}")
    private String jdbcDriver;

    @Bean
    public DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(jdbcDriver);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(jdbcUser);
        hikariConfig.setPassword(passwd);

        hikariConfig.setMaximumPoolSize(connectionNumber);

        return new HikariDataSource(hikariConfig);
    }
}
