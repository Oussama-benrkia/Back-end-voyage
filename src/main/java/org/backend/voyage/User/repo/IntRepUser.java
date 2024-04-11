package org.backend.voyage.User.repo;

import org.backend.voyage.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.BiPredicate;

@Repository
public interface IntRepUser extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
