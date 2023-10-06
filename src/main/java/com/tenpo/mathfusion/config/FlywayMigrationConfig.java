package com.tenpo.mathfusion.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;



@Configuration
public class FlywayMigrationConfig {

    private final Flyway flyway;

    public FlywayMigrationConfig(Flyway flyway) {
        this.flyway = flyway;
    }

    @PostConstruct
    public void migrateDatabase() {
        System.out.println("Migrating database...");
        flyway.migrate();
    }
}