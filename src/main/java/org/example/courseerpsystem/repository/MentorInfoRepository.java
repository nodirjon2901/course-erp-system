package org.example.courseerpsystem.repository;

import org.example.courseerpsystem.domain.entity.MentorInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MentorInfoRepository extends JpaRepository<MentorInfoEntity, UUID> {

    Optional<MentorInfoEntity> findByUserId(UUID userId);

}
