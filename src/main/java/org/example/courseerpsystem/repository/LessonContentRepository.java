package org.example.courseerpsystem.repository;

import org.example.courseerpsystem.domain.entity.LessonContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LessonContentRepository extends JpaRepository<LessonContentEntity, UUID> {

    List<LessonContentEntity> findByLessonId(UUID lessonId);

}
