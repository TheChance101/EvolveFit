package com.thechance.evolvefit.service.entity.workout

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class WorkoutHistory(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    val workout: Workout,
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    val createdAt: LocalDateTime,
    @Column(nullable = false)
    val durationSeconds: Int
)
