package com.thechance.evolvefit.repository.workout

import com.thechance.evolvefit.service.entity.workout.Workout
import com.thechance.evolvefit.service.entity.workout.WorkoutCreatedBy
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WorkoutRepository: JpaRepository<Workout, UUID> {
    fun findAllByCreatedBy(createdBy: WorkoutCreatedBy): List<Workout>
}