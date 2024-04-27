package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.enums.LessonStatus;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "lessons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonEntity extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private ModuleEntity module;

    private LocalDate date;

    private Integer numeric;

    private String topic;

    private String description;

    @Enumerated(EnumType.STRING)
    private LessonStatus status;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LessonContentEntity> contents;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AttendanceEntity> attendances;
}
