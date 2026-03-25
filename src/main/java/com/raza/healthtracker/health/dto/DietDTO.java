package com.raza.healthtracker.health.dto;

import lombok.Data;

@Data
public class DietDTO {
    private String mealType;
    private String foodName;
    private Integer calories;
    private Integer protein;
}