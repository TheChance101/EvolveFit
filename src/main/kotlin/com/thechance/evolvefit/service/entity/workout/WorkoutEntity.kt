package com.thechance.evolvefit.service.entity.workout

import java.util.*

data class WorkoutEntity(
    val id: UUID,
    val name: String,
    val description: String,
    val level: WorkoutLevel,
    val imageUrl: String,
    val creatorName: String,
    val exercises: List<Exercise>
)
