package com.thechance.evolvefit.repository.workout

import com.thechance.evolvefit.service.entity.workout.WorkoutHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WorkoutHistoryRepository: JpaRepository<WorkoutHistory, UUID> {
}