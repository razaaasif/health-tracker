package com.raza.healthtracker.health.repository;

import com.raza.healthtracker.health.entity.DailyHealthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyHealthLogRepository extends JpaRepository<DailyHealthLog, Long> {

    Optional<DailyHealthLog> findByPatientIdAndLogDate(Long patientId, LocalDate date);
}