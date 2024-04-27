package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.entity.LessonEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonContentResponseDTO {

    private UUID id;

    private LessonEntity lesson;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
