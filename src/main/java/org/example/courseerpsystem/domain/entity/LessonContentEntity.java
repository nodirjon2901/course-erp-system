package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "lesson_contents")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonContentEntity extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private LessonEntity lesson;

    private String description;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<FileUploadEntity> files;
}
