package com.raza.healthtracker.health.dto;

import lombok.Data;

@Data
public class MedicineDTO {
    private String medicineName;
    private String dosage;
    private Boolean taken;
}