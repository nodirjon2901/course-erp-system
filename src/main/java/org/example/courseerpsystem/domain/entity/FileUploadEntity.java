package org.example.courseerpsystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileUploadEntity extends BaseEntity{

    @ManyToOne
    @JsonIgnore
    private LessonContentEntity content;

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long size;

}
