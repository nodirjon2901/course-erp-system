package org.example.courseerpsystem.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseResponseDTO {

    private UUID id;

    private String name;

    private String description;

    private Integer numberOfModules;

    private Double price;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
