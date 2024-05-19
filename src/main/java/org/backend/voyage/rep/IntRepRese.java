package org.backend.voyage.rep;

import org.backend.voyage.model.Reservation;
import org.backend.voyage.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IntRepRese extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByUser(User user);
    Page<Reservation> findAllByUser(User user, Pageable pageable);
    List<Reservation> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

}
