package com.thechance.evolvefit.service.favorite

import com.thechance.evolvefit.repository.favorite.FavoriteWorkoutRepository
import com.thechance.evolvefit.service.entity.favorite.FavoriteWorkout
import com.thechance.evolvefit.service.entity.workout.Workout
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class FavoriteWorkoutService(
    private val favoriteWorkoutRepository: FavoriteWorkoutRepository,
    private val entityManager: EntityManager,
) {

    fun addFavoriteWorkout(userId: UUID, workoutId: UUID): FavoriteWorkout {
        val isExist = favoriteWorkoutRepository.existsFavoriteWorkoutByUserIdAndWorkoutId(userId, workoutId)
        if (isExist) throw IllegalStateException("Favorite workout already exists")

        val workoutRef = entityManager.getReference(Workout::class.java, workoutId)
        val favoriteWorkout = FavoriteWorkout(
            userId = userId,
            workout = workoutRef,
            createdAt = LocalDateTime.now()
        )
        return favoriteWorkoutRepository.save(favoriteWorkout)
    }

    fun getAllFavoriteWorkouts(userId: UUID): List<FavoriteWorkout> {
        return favoriteWorkoutRepository.findAllByUserId(userId)
    }

    @Transactional
    fun removeFavoriteWorkout(userId: UUID, workoutId: UUID) {
        val isDeleted = favoriteWorkoutRepository.removeFavoriteWorkoutByUserIdAndWorkoutId(userId, workoutId) > 0
        if (!isDeleted) throw IllegalStateException("Favorite workout not found")
    }
}
