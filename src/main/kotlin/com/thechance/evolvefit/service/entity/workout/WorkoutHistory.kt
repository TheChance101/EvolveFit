package com.thechance.evolvefit.service.entity.workout

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
data class WorkoutHistory(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    val workoutId: UUID,
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    val createdAt: LocalDateTime,
    @Column(nullable = false)
    val durationSeconds: Int
)
