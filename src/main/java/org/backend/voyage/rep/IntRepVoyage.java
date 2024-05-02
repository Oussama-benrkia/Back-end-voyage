package org.backend.voyage.rep;

import org.backend.voyage.model.Voyage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntRepVoyage extends JpaRepository<Voyage, Long> {
    List<Voyage> findAll(Specification<Voyage> specification);
    Page<Voyage> findAll(Specification<Voyage> specification, Pageable pageable);

}
