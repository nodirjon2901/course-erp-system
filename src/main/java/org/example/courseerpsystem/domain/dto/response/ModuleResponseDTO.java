package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.entity.CourseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuleResponseDTO {

    private UUID id;

    private CourseEntity course;

    private Integer numeric;

    private String description;

    private Integer lessonAmount;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
