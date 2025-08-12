package com.thechance.evolvefit.api.dto.workout

import jakarta.validation.constraints.Min
import java.util.*

data class WorkoutHistoryRequest(
    val workoutId: UUID,
    @field:Min(1, message = "duration must be larger than 0")
    val durationSeconds: Int
)