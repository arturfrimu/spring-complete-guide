package com.arturfrimu.googledrive.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.util.Collections;

@Configuration
public class GoogleDriveConfig {

    @Value("${google.credentials.file.path}")
    private String credentialsFilePath;

    @Bean
    public Drive driveService() throws Exception {
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                GoogleCredential.fromStream(new FileInputStream(credentialsFilePath))
                        .createScoped(Collections.singleton(DriveScopes.DRIVE)))
                .build();
    }
}
