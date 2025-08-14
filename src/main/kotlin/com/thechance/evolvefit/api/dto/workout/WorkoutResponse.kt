package com.thechance.evolvefit.api.dto.workout

import com.thechance.evolvefit.service.entity.workout.BodyArea
import com.thechance.evolvefit.service.entity.workout.WorkoutEntity
import com.thechance.evolvefit.service.workout.WorkoutService
import java.util.*

data class WorkoutResponse(
    val id: UUID,
    val name: String,
    val durationSeconds: Int,
    val imageUrl: String,
    val focusArea: Set<BodyArea>
)

fun WorkoutEntity.toWorkoutResponse(): WorkoutResponse {
    val focusAreas = mutableSetOf<BodyArea>()
    exercises.forEach { focusAreas.addAll(it.focusArea) }

    return WorkoutResponse(
        id = id,
        name = name,
        durationSeconds = WorkoutService.calculateWorkoutDuration(exercises),
        imageUrl = imageUrl,
        focusArea = focusAreas
    )
}