package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceEntity extends BaseEntity {

    @ManyToOne
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JsonIgnore
    private LessonEntity lesson;

    private String status;

}

