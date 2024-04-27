package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.entity.LessonContentEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileUploadResponseDTO {

    protected UUID id;

    private LessonContentEntity lessonContent;

    private String fileName;

    private String fileDownloadUri;

    private String uriBody;

    private String fileType;

    private long size;

    protected LocalDateTime createdDate;

    protected LocalDateTime updatedDate;

}
