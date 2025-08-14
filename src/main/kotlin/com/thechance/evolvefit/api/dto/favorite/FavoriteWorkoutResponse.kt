package com.thechance.evolvefit.api.dto.favorite

import com.thechance.evolvefit.service.entity.favorite.FavoriteWorkout
import com.thechance.evolvefit.service.entity.workout.BodyArea
import com.thechance.evolvefit.service.workout.WorkoutService
import java.util.*

data class FavoriteWorkoutResponse(
    val workoutId: UUID,
    val name: String,
    val durationSeconds: Int,
    val focusArea: Set<BodyArea>,
    val imageUrl: String,
)

fun FavoriteWorkout.toFavoriteWorkoutResponse() = FavoriteWorkoutResponse(
    workoutId = workout.id,
    name = workout.name,
    durationSeconds = WorkoutService.calculateWorkoutDuration(workout.exercises),
    focusArea = WorkoutService.getWorkoutFocusAreas(workout.exercises),
    imageUrl = workout.imageUrl,
)
