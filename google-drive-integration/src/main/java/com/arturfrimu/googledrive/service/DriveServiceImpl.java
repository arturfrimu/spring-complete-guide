package com.arturfrimu.googledrive.service;

import com.arturfrimu.googledrive.dto.Response;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DriveServiceImpl {

    Drive driveService;

    @SneakyThrows
    public Response uploadImageToDrive(File file) {
        String folderId = "182FjqtvIrsqpnUypXcIJKMf42YzclmXo";

        com.google.api.services.drive.model.File fileMetadata = new com.google.api.services.drive.model.File();
        fileMetadata.setName(file.getName());
        fileMetadata.setParents(Collections.singletonList(folderId));
        FileContent mediaContent = new FileContent(
                "image/jpeg", file
        );
        com.google.api.services.drive.model.File uploadedFile = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        String imageUrl = "https://drive.google.com/uc?export=view&id=" + uploadedFile.getId();
        file.delete();
        return Response.builder()
                .status(200)
                .message("File uploaded successfully")
                .url(imageUrl)
                .build();
    }
}
