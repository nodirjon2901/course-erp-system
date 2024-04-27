package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.courseerpsystem.domain.enums.EducationType;

import java.util.List;

@Entity(name = "groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnore
    private CourseEntity course;

    @ManyToOne
    @JsonIgnore
    private MentorInfoEntity mentor;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    private boolean isEmpty;

    @ManyToMany
    @JsonIgnore
    private List<UserEntity> users;

    @ManyToMany(mappedBy = "groups",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ModuleEntity> modules;

}
