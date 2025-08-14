package com.thechance.evolvefit.repository.workout

import com.thechance.evolvefit.service.entity.workout.Exercise
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ExerciseRepository: JpaRepository<Exercise, UUID> {
    fun searchByNameContaining(name: String): List<Exercise>
}