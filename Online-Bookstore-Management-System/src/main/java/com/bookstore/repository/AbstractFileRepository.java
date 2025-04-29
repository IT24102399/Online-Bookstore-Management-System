package com.bookstore.repository;

import com.bookstore.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractFileRepository<T extends BaseEntity> implements FileRepository<T> {

    @Value("${app.storage.location}")
    private String storageLocation;

    private final Class<T> entityClass;
    private final String fileName;
    private List<T> entities = new ArrayList<>();

    protected AbstractFileRepository(Class<T> entityClass, String fileName) {
        this.entityClass = entityClass;
        this.fileName = fileName;
    }

    @PostConstruct
    public void init() {
        File directory = new File(storageLocation);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(storageLocation + "/" + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                saveToFile();
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage: " + e.getMessage());
            }
        } else {
            loadFromFile();
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public Optional<T> findById(String id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    @Override
    public T save(T entity) {
        if (entity.getId() == null || entity.getId().isEmpty()) {
            // Create new entity
            entity.setId(UUID.randomUUID().toString());
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            entities.add(entity);
        } else {
            // Update existing entity
            entity.setUpdatedAt(LocalDateTime.now());
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i).getId().equals(entity.getId())) {
                    entities.set(i, entity);
                    break;
                }
            }
        }

        saveToFile();
        return entity;
    }

    @Override
    public void deleteById(String id) {
        entities = entities.stream()
                .filter(entity -> !entity.getId().equals(id))
                .collect(Collectors.toList());
        saveToFile();
    }

    @Override
    public boolean existsById(String id) {
        return entities.stream()
                .anyMatch(entity -> entity.getId().equals(id));
    }

    private void loadFromFile() {
        try {
            Path path = Paths.get(storageLocation, fileName);
            if (Files.size(path) > 0) {
                String content = new String(Files.readAllBytes(path));
                if (!content.trim().isEmpty() && !content.trim().equals("[]")) {
                    ObjectMapper mapper = createObjectMapper();
                    entities = mapper.readValue(path.toFile(),
                            mapper.getTypeFactory().constructCollectionType(List.class, entityClass));
                }
            }
        } catch (IOException e) {
            // If there's an error, start with an empty list
            entities = new ArrayList<>();
        }
    }

    private void saveToFile() {
        try {
            ObjectMapper mapper = createObjectMapper();
            mapper.writeValue(new File(storageLocation + "/" + fileName), entities);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage());
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        return mapper;
    }
}
