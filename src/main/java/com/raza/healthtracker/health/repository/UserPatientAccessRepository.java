package com.raza.healthtracker.health.repository;

import com.raza.healthtracker.health.entity.UserPatientAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPatientAccessRepository extends JpaRepository<UserPatientAccess, Long> {
    Optional<UserPatientAccess> findByUserIdAndPatientId(Long userId, Long patientId);
}