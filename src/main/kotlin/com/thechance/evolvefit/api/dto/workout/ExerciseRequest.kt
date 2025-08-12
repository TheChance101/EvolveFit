package com.thechance.evolvefit.api.dto.workout

import com.thechance.evolvefit.service.entity.workout.BodyArea
import com.thechance.evolvefit.service.entity.workout.ExerciseType
import jakarta.validation.constraints.NotBlank

data class ExerciseRequest(
    @field:NotBlank(message = "name must not be blank")
    val name: String,
    val gymEquipments: List<Long>,
    val instructions: List<String>,
    val focusArea: List<BodyArea>,
    val exerciseType: ExerciseType,
    val durationSeconds: Int? = null,
    val reps: Int? = null,
)

