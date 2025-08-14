package com.thechance.evolvefit.repository.favorite

import com.thechance.evolvefit.service.entity.favorite.FavoriteWorkout
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoriteWorkoutRepository: JpaRepository<FavoriteWorkout, UUID> {
    fun findAllByUserId(userId: UUID): List<FavoriteWorkout>
    fun removeFavoriteWorkoutByUserIdAndWorkoutId(userId: UUID, workoutId: UUID): Int
    fun existsFavoriteWorkoutByUserIdAndWorkoutId(userId: UUID, workoutId: UUID): Boolean
}
