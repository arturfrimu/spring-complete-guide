package com.arturfrimu.mongo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

public final class JsonResourceLoader {

    public static <T> T load(final String path, Class<T> clazz) throws IOException {
        Resource resource = new ClassPathResource(path);
        String json = new String(Files.readAllBytes(resource.getFile().toPath()));
        return new ObjectMapper().readValue(json, clazz);
    }

    public static String load(final String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }
}
