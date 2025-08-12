package com.thechance.evolvefit.service.workout

import com.thechance.evolvefit.api.dto.workout.WorkoutHistoryRequest
import com.thechance.evolvefit.api.dto.workout.WorkoutRequest
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.repository.workout.ExerciseRepository
import com.thechance.evolvefit.repository.workout.WorkoutHistoryRepository
import com.thechance.evolvefit.repository.workout.WorkoutRepository
import com.thechance.evolvefit.service.ImageService
import com.thechance.evolvefit.service.entity.workout.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@Service
class WorkoutService(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val imageService: ImageService,
    private val userRepository: UserRepository,
    private val workoutHistoryRepository: WorkoutHistoryRepository
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

    fun suggestWorkoutsForUser(userId: UUID): List<Workout> {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }
        val allWorkouts = workoutRepository.findAllByCreatedBy(WorkoutCreatedBy.SYSTEM)

        return allWorkouts.filter { workout ->
            workout.exercises.all { exercise ->
                exercise.gymEquipments.isEmpty() || exercise.gymEquipments.all { equipment ->
                    user.gymEquipments.contains(equipment)
                }
            }
        }
    }

    fun getAllWorkouts(): List<Workout> {
        return workoutRepository.findAllByCreatedBy(WorkoutCreatedBy.SYSTEM)
    }

    fun getAllCommunityWorkouts(): List<Workout> {
        return workoutRepository.findAllByCreatedBy(WorkoutCreatedBy.USER)
    }

    fun setWorkoutImage(workoutId: UUID, image: MultipartFile): String {
        val workout = workoutRepository.findById(workoutId).orElseThrow { throw IllegalStateException("Workout not found") }

        if (workout.imageUrl.isNotBlank()) {
            imageService.deleteImage(workout.imageUrl)
        }

        val imageUrl = imageService.uploadWorkoutImage(workoutId.toString(), image)
        val updatedWorkout = workout.copy(imageUrl = imageUrl)
        workoutRepository.save(updatedWorkout)
        return imageUrl
    }

    fun submitWorkout(userId: UUID, workoutHistoryRequest: WorkoutHistoryRequest) {
        val workoutHistory = WorkoutHistory(
            workoutId = workoutHistoryRequest.workoutId,
            userId = userId,
            createdAt = LocalDateTime.now(),
            durationSeconds = workoutHistoryRequest.durationSeconds,
        )

        workoutHistoryRepository.save(workoutHistory)
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