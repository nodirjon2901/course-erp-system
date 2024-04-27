package org.example.courseerpsystem.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.enums.EducationType;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupCreateDTO {

    private String name;

    private UUID courseId;

    private UUID mentorId;

    private Integer capacity;

    private EducationType educationType;

}
