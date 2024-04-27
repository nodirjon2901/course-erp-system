package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.entity.CourseEntity;
import org.example.courseerpsystem.domain.entity.UserEntity;
import org.example.courseerpsystem.domain.enums.EducationType;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupResponseDTO {

    private UUID id;

    private String name;

    private CourseEntity courseEntity;

    private UserEntity mentor;

    private Integer capacity;

    private EducationType educationType;

    private boolean isEmpty;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
