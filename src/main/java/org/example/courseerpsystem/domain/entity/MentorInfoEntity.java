package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "mentors_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MentorInfoEntity extends BaseEntity{

    @OneToOne
    @JsonIgnore
    private UserEntity user;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GroupEntity> groups;

    private String expertise;

}
