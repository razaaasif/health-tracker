package com.raza.healthtracker.health.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercise_entry", schema = "health_tracker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "log_id")
    private DailyHealthLog log;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "duration_min")
    private Integer durationMin;

    @Column(name = "calories_burned")
    private Integer caloriesBurned;
}