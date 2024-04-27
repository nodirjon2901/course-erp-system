package org.example.courseerpsystem.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonCreateDTO {

    private UUID moduleId;

    private LocalDate date;

    private Integer numeric;

    private String topic;

    private String description;

}
