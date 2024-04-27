package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.entity.ModuleEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonResponseDTO {

    private UUID id;

    private ModuleEntity module;

    private LocalDate date;

    private Integer numeric;

    private String topic;

    private String description;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
