package com.raza.healthtracker.health.dto;

import lombok.Data;

import java.util.List;

@Data
public class SaveHealthLogInput {
    private Long patientId;
    private Integer steps;
    private Integer calories;
    private Integer protein;
    private Integer water;

    private List<DietDTO> diet;
    private List<ExerciseDTO> exercises;
    private List<MedicineDTO> medicines;
}