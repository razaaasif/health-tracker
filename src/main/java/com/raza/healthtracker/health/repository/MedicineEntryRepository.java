package com.raza.healthtracker.health.repository;

import com.raza.healthtracker.health.entity.MedicineEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineEntryRepository extends JpaRepository<MedicineEntry, Long> {}