package com.raza.healthtracker.health.repository;

import com.raza.healthtracker.health.entity.ExerciseEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseEntryRepository extends JpaRepository<ExerciseEntry, Long> {}