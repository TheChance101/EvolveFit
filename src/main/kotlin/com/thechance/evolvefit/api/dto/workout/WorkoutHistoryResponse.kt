package com.thechance.evolvefit.api.dto.workout

import com.thechance.evolvefit.service.entity.workout.WorkoutLevel
import java.time.LocalDateTime

data class WorkoutHistoryResponse(
    val name: String,
    val imageUrl: String,
    val createdAt: LocalDateTime,
    val exercisesCount: Int,
    val durationSeconds: Int,
    val level: WorkoutLevel
)
