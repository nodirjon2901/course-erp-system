package org.example.courseerpsystem.service;

import java.util.List;
import java.util.UUID;

/**
 * @param <CD> Create DTO
 * @param <RD> Read DTO
 * @author Nodirjon
 */
public interface BaseService<RD, CD> {

    void save(CD cd);

    void delete(UUID id);

    RD update(CD cd, UUID id);

    RD findById(UUID id);

    List<RD> findAll();

}
