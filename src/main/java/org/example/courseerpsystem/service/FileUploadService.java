package org.example.courseerpsystem.service;

import org.example.courseerpsystem.domain.dto.response.FileUploadResponseDTO;
import org.example.courseerpsystem.domain.entity.FileUploadEntity;
import org.example.courseerpsystem.exception.DataNotFoundException;
import org.example.courseerpsystem.repository.FileUploadRepository;
import org.example.courseerpsystem.repository.LessonContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUploadService {

    private final Path fileLocation;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private LessonContentRepository contentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public FileUploadService(@Value("${file.upload-dir}") String fileUploadDir) {
        this.fileLocation = Paths.get(fileUploadDir)
                .toAbsolutePath().normalize();
    }

    public FileUploadResponseDTO save(MultipartFile file, UUID contentId) {
        String fullFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path targetLocation = fileLocation.resolve(fullFileName);
            Files.copy(file.getInputStream(), targetLocation);
        } catch (FileAlreadyExistsException e) {
            String[] fileNameAndType = fullFileName.split("\\.");
            fullFileName = fileNameAndType[0] + System.currentTimeMillis() + "." + fileNameAndType[1];
            Path targetLocation = fileLocation.resolve(fullFileName);
            try {
                Files.copy(file.getInputStream(), targetLocation);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileUploadEntity entity = FileUploadEntity.builder()
                .content(contentRepository.findById(contentId).get())
                .fileName(fullFileName)
                .fileType(file.getContentType())
                .fileDownloadUri(fileLocation + file.getOriginalFilename())
                .size(file.getSize())
                .build();
        return modelMapper.map(fileUploadRepository.save(entity), FileUploadResponseDTO.class);
    }

    public Path downloadFile(String fileName) {
        FileUploadEntity entity = fileUploadRepository.findByFileName(fileName).orElseThrow(
                () -> new DataNotFoundException("File is not found with this name")
        );
        return fileLocation.resolve(entity.getFileName());
    }

}
