package org.example.courseerpsystem.repository;

import org.example.courseerpsystem.domain.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {

    List<LessonEntity> findByModuleId(UUID id);

    Optional<LessonEntity> findByNumeric(Integer num);

}
