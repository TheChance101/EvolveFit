package com.thechance.evolvefit.service.workout

import com.thechance.evolvefit.api.dto.workout.WorkoutRequest
import com.thechance.evolvefit.repository.workout.ExerciseRepository
import com.thechance.evolvefit.repository.workout.WorkoutRepository
import com.thechance.evolvefit.service.entity.workout.Exercise
import com.thechance.evolvefit.service.entity.workout.ExerciseType
import com.thechance.evolvefit.service.entity.workout.Workout
import org.springframework.stereotype.Service
import java.util.*

@Service
class WorkoutService(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
) {

    fun createWorkout(userId: UUID, workoutRequest: WorkoutRequest): Workout {
        val workout = Workout(
            name = workoutRequest.name,
            description = workoutRequest.description,
            level = workoutRequest.level,
            createdBy = workoutRequest.createdBy,
            creatorId = userId,
            exercises = workoutRequest.exercises.map {
                exerciseRepository.findById(it).orElseThrow { throw IllegalStateException("Exercise not found") }
            }
        )

        return workoutRepository.save(workout)
    }

    fun getAllWorkouts(): List<Workout> {
        return workoutRepository.findAll()
    }

    companion object {
        private const val SECONDS_PER_REP = 3

        fun calculateWorkoutDuration(exercises: List<Exercise>): Int {
            return exercises.sumOf { exercise ->
                when (exercise.exerciseType) {
                    ExerciseType.DURATION -> exercise.durationSeconds ?: 0
                    ExerciseType.REPS -> (exercise.reps ?: 0) * SECONDS_PER_REP
                }
            }
        }
    }
}