package org.example.courseerpsystem.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuleCreateDTO {

    private UUID courseId;

    private Integer numeric;

    private String description;

    private Integer lessonAmount;

}
