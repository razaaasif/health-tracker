package com.raza.healthtracker.health.entity;

import com.raza.healthtracker.auth.entity.User;
import com.raza.healthtracker.health.enums.AccessType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_patient_access",
        schema = "health_tracker",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "patient_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPatientAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type", nullable = false)
    private AccessType accessType;

    @CreationTimestamp
    private LocalDateTime grantedAt;
}