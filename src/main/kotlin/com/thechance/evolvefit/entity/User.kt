package com.thechance.evolvefit.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    @Email
    val email: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val birthday: LocalDate,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val gender: Gender,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val measurementType: MeasurementType,
    @Column(nullable = false)
    val height: Float,
    @Column(nullable = false)
    val weight: Float,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val goal: Goal,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_workout_days", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "workout_days")
    @Enumerated(EnumType.STRING)
    val workoutDays: Set<WorkoutDays> = setOf(),

    @ManyToMany
    @JoinTable(
        name = "user_equipments",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "equipment_id")]
    )
    val gymEquipments: List<GymEquipment> = listOf(),

    var imageUrl: String = ""
)

enum class Gender {
    MALE, FEMALE
}

enum class MeasurementType {
    METRIC, IMPERIAL
}

enum class Goal {
    LOSE_WEIGHT, GAIN_WEIGHT, STAY_IN_SHAPE
}

enum class WorkoutDays {
    SATURATED, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
}