package com.raza.healthtracker.health.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicine_entry", schema = "health_tracker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "log_id")
    private DailyHealthLog log;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    private String dosage;

    private Boolean taken = false;
}