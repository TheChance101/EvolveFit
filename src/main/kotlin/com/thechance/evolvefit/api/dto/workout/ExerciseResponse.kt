package com.thechance.evolvefit.api.dto.workout

import com.thechance.evolvefit.service.entity.GymEquipment
import com.thechance.evolvefit.service.entity.workout.BodyArea
import com.thechance.evolvefit.service.entity.workout.Exercise
import com.thechance.evolvefit.service.entity.workout.ExerciseType
import java.util.*

data class ExerciseResponse(
    val id: UUID,
    val name: String,
    val instructions: List<String>,
    val images: List<String>,
    val gymEquipments: List<GymEquipment>,
    val focusArea: List<BodyArea>,
    val exerciseType: ExerciseType,
    val durationSeconds: Int? = null,
    val reps: Int? = null,
)

fun Exercise.toExerciseResponse() = ExerciseResponse(
    id = id,
    name = name,
    instructions = instructions,
    images = images,
    gymEquipments = gymEquipments,
    focusArea = focusArea,
    exerciseType = exerciseType,
    durationSeconds = durationSeconds,
    reps = reps
)