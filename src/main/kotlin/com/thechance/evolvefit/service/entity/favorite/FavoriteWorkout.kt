package com.thechance.evolvefit.service.entity.favorite

import com.thechance.evolvefit.service.entity.workout.Workout
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class FavoriteWorkout(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    val workout: Workout,
    val createdAt: LocalDateTime
)
