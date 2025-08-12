package com.thechance.evolvefit.api.dto.workout

import com.thechance.evolvefit.service.entity.workout.WorkoutCreatedBy
import com.thechance.evolvefit.service.entity.workout.WorkoutLevel
import jakarta.validation.constraints.NotBlank
import java.util.*

data class WorkoutRequest(
    @field:NotBlank(message = "name must not be blank")
    val name: String,
    val description: String,
    val level: WorkoutLevel,
    val createdBy: WorkoutCreatedBy,
    val exercises: List<UUID>
)
