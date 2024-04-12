package org.backend.voyage.User.repo;

import org.backend.voyage.User.Model.Enum.Role;
import org.backend.voyage.User.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

@Repository
public interface IntRepUser extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE LOWER(u.first_name) LIKE LOWER(CONCAT('%', :firstName, '%')) OR LOWER(u.Last_name) LIKE LOWER(CONCAT('%', :lastName, '%')) AND u.role = :role")
    List<User> findalluser(String firstName, String lastName, Role role);
    @Query("SELECT u FROM User u WHERE LOWER(u.first_name) LIKE LOWER(CONCAT('%', :firstName, '%')) OR LOWER(u.Last_name) LIKE LOWER(CONCAT('%', :lastName, '%')) AND u.role = :role")
    Page<User> findalluser(String firstName, String lastName, Role role ,Pageable pageable);
}
