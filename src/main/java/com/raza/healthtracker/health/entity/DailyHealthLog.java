package com.raza.healthtracker.health.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "daily_health_log",
        schema = "health_tracker",
        uniqueConstraints = @UniqueConstraint(columnNames = {"patient_id", "log_date"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyHealthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    private Integer steps;
    private Integer calories;

    @Column(name = "protein_grams")
    private Integer proteinGrams;

    @Column(name = "water_ml")
    private Integer waterMl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DietEntry> dietEntries;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseEntry> exerciseEntries;

    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicineEntry> medicineEntries;
}