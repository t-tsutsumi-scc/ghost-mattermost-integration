package app.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    public static final String DATA_SOURCE_PROPERTIES_NAME = "dataSourceProperties";
    public static final String DATA_SOURCE_NAME = "dataSource";
    public static final String JDBC_TEMPLATE_NAME = "jdbcTemplate";

    @Bean(DATA_SOURCE_PROPERTIES_NAME)
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = DATA_SOURCE_NAME)
    @Primary
    public DataSource dataSource(@Qualifier(DATA_SOURCE_PROPERTIES_NAME) DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(JDBC_TEMPLATE_NAME)
    @Primary
    public JdbcTemplate jdbcTemplate(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
