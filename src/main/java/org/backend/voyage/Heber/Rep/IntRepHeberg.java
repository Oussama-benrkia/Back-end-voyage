package org.backend.voyage.Heber.Rep;

import org.backend.voyage.Heber.Model.Hebergement;
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
