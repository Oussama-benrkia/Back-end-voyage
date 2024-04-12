package org.backend.voyage.Activity.Rep;

import org.backend.voyage.Activity.Model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntRepActivi extends JpaRepository<Activity, Long> {
List<Activity> findAllByNomContaining(String nom);
    Page<Activity> findAllByNomContaining(String nom, Pageable pageable);

}
