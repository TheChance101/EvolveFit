package com.thechance.evolvefit.service.entity.workout

import com.thechance.evolvefit.service.entity.GymEquipment
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Exercise(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    val name: String,
    @ElementCollection(fetch = FetchType.EAGER)
    val images: List<String> = listOf(),
    @ManyToMany
    @JoinTable(
        name = "exercise_equipments",
        joinColumns = [JoinColumn(name = "exercise_id")],
        inverseJoinColumns = [JoinColumn(name = "equipment_id")]
    )
    val gymEquipments: List<GymEquipment>,
    @ElementCollection(fetch = FetchType.EAGER)
    val instructions: List<String>,
    @Enumerated(EnumType.STRING)
    val focusArea: List<BodyArea>,
    @Enumerated(EnumType.STRING)
    val exerciseType: ExerciseType,
    val durationSeconds: Int? = null,
    val reps: Int? = null,
    val createdAt: LocalDateTime,
    val creatorId: UUID
)

enum class ExerciseType {
    DURATION,
    REPS
}

enum class BodyArea {
    CHEST,
    BACK,
    LEGS,
    SHOULDERS,
    ARMS,
    CORE
}

//enum class ExerciseCreatedBy {
//    SYSTEM, USER
//}
