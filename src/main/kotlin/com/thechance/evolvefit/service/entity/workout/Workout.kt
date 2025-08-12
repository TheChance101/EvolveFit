package com.thechance.evolvefit.service.entity.workout

import jakarta.persistence.*
import java.util.*

@Entity
data class Workout(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    val name: String,
    val description: String,
    @Enumerated(EnumType.STRING)
    val level: WorkoutLevel,
    val imageUrl: String = "",
    @Enumerated(EnumType.STRING)
    val createdBy: WorkoutCreatedBy,
    val creatorId: UUID,
    @ManyToMany
    @JoinTable(
        name = "workout_exercises",
        joinColumns = [JoinColumn(name = "workout_id")],
        inverseJoinColumns = [JoinColumn(name = "exercise_id")]
    )
    val exercises: List<Exercise>
)

enum class WorkoutLevel {
    BEGINNER, INTERMEDIATE, ADVANCED
}


enum class WorkoutCreatedBy {
    SYSTEM, USER
}
