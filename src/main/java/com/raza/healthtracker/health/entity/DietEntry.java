package com.raza.healthtracker.health.entity;


import com.raza.healthtracker.health.enums.MealType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diet_entry", schema = "health_tracker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "log_id")
    private DailyHealthLog log;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    private Integer calories;
    private Integer protein;
}