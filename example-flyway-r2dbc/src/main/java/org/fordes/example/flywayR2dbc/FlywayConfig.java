package org.fordes.example.flywayR2dbc;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fordes on 2023/10/31
 */
@Configuration
@EnableConfigurationProperties({R2dbcProperties.class, FlywayProperties.class})
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
        String url = r2dbcProperties.getUrl().replaceFirst("r2dbc", "jdbc");
        return Flyway.configure()
                .dataSource(url, r2dbcProperties.getUsername(), r2dbcProperties.getPassword())
                .locations(flywayProperties.getLocations().toArray(String[]::new))
                .baselineOnMigrate(true)
                .load();
    }
}