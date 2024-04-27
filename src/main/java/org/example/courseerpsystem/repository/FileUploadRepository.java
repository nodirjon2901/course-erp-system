package org.example.courseerpsystem.repository;

import org.example.courseerpsystem.domain.entity.FileUploadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUploadEntity, UUID> {

    Optional<FileUploadEntity> findByFileName(String fileName);

}
