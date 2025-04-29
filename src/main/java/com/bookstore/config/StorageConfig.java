package com.bookstore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class StorageConfig {

    @Value("${app.storage.location}")
    private String storageLocation;

    @Bean
    public File initStorage() {
        File directory = new File(storageLocation);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }
}
