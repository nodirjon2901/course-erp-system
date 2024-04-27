package org.example.courseerpsystem.repository;

import org.example.courseerpsystem.domain.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, UUID> {

    List<AttendanceEntity> findByUserId(UUID userId);

    List<AttendanceEntity> findByLessonId(UUID lessonId);

}
