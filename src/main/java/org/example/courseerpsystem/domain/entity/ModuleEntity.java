package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "modules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuleEntity extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private CourseEntity course;

    private Integer numeric;

    private String description;

    private Integer lessonAmount;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LessonEntity> lessonList;

    @ManyToMany
    @JsonIgnore
    private List<GroupEntity> groups;

}
