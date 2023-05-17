package backend.module.dao.jdbc;

import backend.module.Config;
import backend.module.ConfigFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {

    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            Config config = ConfigFactory.getConfig();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(config.getJdbcDriver());

            hikariConfig.setJdbcUrl("jdbc:mysql://" + config.getJdbcUrl()
                    + "/" + config.getJdbcDatabase() + "?allowPublicKeyRetrieval=true&useSSL=false");
            hikariConfig.setUsername(config.getJdbcUser());
            hikariConfig.setPassword(config.getJdbcPassword());

            hikariConfig.setMaximumPoolSize(config.getJdbcPoolSize());

            dataSource = new HikariDataSource(hikariConfig);
        }
        return dataSource;
    }
}
