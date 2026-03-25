package com.raza.healthtracker.health.dto;

import lombok.Data;

@Data
public class ExerciseDTO {
    private String exerciseName;
    private Integer durationMin;
    private Integer caloriesBurned;
}
