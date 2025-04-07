package com.arturfrimu.googledrive.controller;


import com.arturfrimu.googledrive.service.DriveServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/google-drive")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DriveController {

    DriveServiceImpl driveService;

    @SneakyThrows
    @PostMapping("/upload")
    public Object uploadFile(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        return driveService.uploadImageToDrive(tempFile);
    }
}
