package com.thechance.evolvefit.api.dto.workout

import com.thechance.evolvefit.service.entity.workout.WorkoutEntity
import com.thechance.evolvefit.service.entity.workout.WorkoutLevel
import com.thechance.evolvefit.service.workout.WorkoutService
import java.util.*

data class WorkoutDetailsResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val level: WorkoutLevel,
    val durationSeconds: Int,
    val imageUrl: String,
    val createdBy: String,
    val exercises: List<ExerciseResponse>,
)

fun WorkoutEntity.toWorkoutDetailsResponse() = WorkoutDetailsResponse(
    id = id,
    name = name,
    description = description,
    level = level,
    durationSeconds = WorkoutService.calculateWorkoutDuration(exercises),
    imageUrl = imageUrl,
    exercises = exercises.map { it.toExerciseResponse() },
    createdBy = creatorName,
)
