package org.example.courseerpsystem.controller;

import lombok.RequiredArgsConstructor;
import org.example.courseerpsystem.domain.dto.response.FileUploadResponseDTO;
import org.example.courseerpsystem.service.FileUploadService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload/{contentId}")
    public ResponseEntity<FileUploadResponseDTO> uploadFile(
            @RequestParam MultipartFile file,
            @PathVariable UUID contentId) {
        return ResponseEntity.ok(fileUploadService.save(file,contentId));
    }

    @PostMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Path file = fileUploadService.downloadFile(fileName);
        try {
            String mediaType = Files.probeContentType(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mediaType))
                    .body(new UrlResource(file.toUri()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
