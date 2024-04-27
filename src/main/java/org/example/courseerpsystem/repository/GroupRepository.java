package org.example.courseerpsystem.repository;

import org.example.courseerpsystem.domain.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {

    Optional<GroupEntity> findByName(String name);

    List<GroupEntity> findByMentorId(UUID mentorId);

    List<GroupEntity> findByCourseId(UUID courseId);

}
