package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.entity.LessonEntity;
import org.example.courseerpsystem.domain.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceResponseDTO {

    private UUID id;

    private UserEntity user;

    private LessonEntity lesson;

    private String status;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
