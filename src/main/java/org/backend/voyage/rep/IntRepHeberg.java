package org.backend.voyage.rep;

import org.backend.voyage.model.Hebergement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntRepHeberg extends JpaRepository<Hebergement,Long> {
    Page<Hebergement> findAllByNomContainingOrVilleContaining(String nom, String ville, Pageable pageable);
    List<Hebergement> findAllByNomContaining(String keyword);
}
