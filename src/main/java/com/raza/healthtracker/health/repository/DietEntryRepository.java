package com.raza.healthtracker.health.repository;

import com.raza.healthtracker.health.entity.DietEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietEntryRepository extends JpaRepository<DietEntry, Long> {}