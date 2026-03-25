package com.raza.healthtracker.health.service;

import com.raza.healthtracker.health.dto.*;
import com.raza.healthtracker.health.entity.*;
import com.raza.healthtracker.health.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final DailyHealthLogRepository logRepo;
    private final UserPatientAccessRepository accessRepo;

    public DailyHealthLog save(Long userId, SaveHealthLogInput input) {

        // 🔐 access check
        accessRepo.findByUserIdAndPatientId(userId, input.getPatientId())
                .orElseThrow(() -> new RuntimeException("Unauthorized"));

        DailyHealthLog log = logRepo
                .findByPatientIdAndLogDate(input.getPatientId(), LocalDate.now())
                .orElse(DailyHealthLog.builder()
                        .patient(Patient.builder().id(input.getPatientId()).build())
                        .logDate(LocalDate.now())
                        .build());

        log.setSteps(input.getSteps());
        log.setCalories(input.getCalories());
        log.setProteinGrams(input.getProtein());
        log.setWaterMl(input.getWater());

        // Diet
        if (input.getDiet() != null) {
            log.setDietEntries(
                    input.getDiet().stream().map(d ->
                            DietEntry.builder()
                                    .log(log)
                                    .foodName(d.getFoodName())
                                    .calories(d.getCalories())
                                    .protein(d.getProtein())
                                    .build()
                    ).collect(Collectors.toList())
            );
        }

        // Exercise
        if (input.getExercises() != null) {
            log.setExerciseEntries(
                    input.getExercises().stream().map(e ->
                            ExerciseEntry.builder()
                                    .log(log)
                                    .exerciseName(e.getExerciseName())
                                    .durationMin(e.getDurationMin())
                                    .caloriesBurned(e.getCaloriesBurned())
                                    .build()
                    ).collect(Collectors.toList())
            );
        }

        // Medicine
        if (input.getMedicines() != null) {
            log.setMedicineEntries(
                    input.getMedicines().stream().map(m ->
                            MedicineEntry.builder()
                                    .log(log)
                                    .medicineName(m.getMedicineName())
                                    .dosage(m.getDosage())
                                    .taken(m.getTaken())
                                    .build()
                    ).collect(Collectors.toList())
            );
        }

        return logRepo.save(log);
    }

    public String analyze(Long userId, Long patientId) {

        accessRepo.findByUserIdAndPatientId(userId, patientId)
                .orElseThrow();

        DailyHealthLog log = logRepo
                .findByPatientIdAndLogDate(patientId, LocalDate.now())
                .orElseThrow();

        StringBuilder res = new StringBuilder();

        if (log.getSteps() < 8000) res.append("Low activity\n");
        if (log.getProteinGrams() < 80) res.append("Low protein\n");
        if (log.getCalories() > 2500) res.append("High calories\n");

        return res.toString();
    }
}