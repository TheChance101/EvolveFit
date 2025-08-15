package com.thechance.evolvefit.service.workout

import com.thechance.evolvefit.api.dto.workout.WorkoutHistoryRequest
import com.thechance.evolvefit.api.dto.workout.WorkoutHistoryResponse
import com.thechance.evolvefit.api.dto.workout.WorkoutRequest
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.repository.workout.ExerciseRepository
import com.thechance.evolvefit.repository.workout.WorkoutHistoryRepository
import com.thechance.evolvefit.repository.workout.WorkoutRepository
import com.thechance.evolvefit.service.ImageService
import com.thechance.evolvefit.service.entity.GymEquipment
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

    fun createWorkout(userId: UUID, workoutRequest: WorkoutRequest): WorkoutEntity {
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

        return workoutRepository.save(workout).toEntity()
    }

    fun suggestWorkoutsForUser(userId: UUID, focusArea: BodyArea?): List<WorkoutEntity> {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }
        val allWorkouts = workoutRepository.findAllByCreatedBy(WorkoutCreatedBy.SYSTEM)

        return allWorkouts
            .filter { workout ->
            workout.exercises.any { exercise -> (isExerciseMatchFocusArea(exercise, focusArea)) } &&
            workout.exercises.all { exercise -> isExerciseMatchUserEquipments(exercise, user.gymEquipments) }
            }
            .map { it.toEntity() }
    }

    fun getAllCommunityWorkouts(focusArea: BodyArea?): List<WorkoutEntity> {
        return workoutRepository.findAllByCreatedBy(WorkoutCreatedBy.USER)
            .filter { workout -> workout.exercises.any { exercise -> isExerciseMatchFocusArea(exercise, focusArea)} }
            .map { it.toEntity() }
    }

    fun getWorkoutDetails(workoutId: UUID): WorkoutEntity {
        val workout = workoutRepository.findById(workoutId).orElseThrow { throw IllegalStateException("Workout not found") }
        return workout.toEntity()
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

    fun getUserWorkoutsHistory(userId: UUID): List<WorkoutHistoryResponse> {
        val workoutHistory = workoutHistoryRepository.findByUserId(userId)

        val workoutIds = workoutHistory.map { it.workoutId }.distinct()
        val workoutsMap = workoutRepository.findAllById(workoutIds).associateBy { it.id }

        return workoutHistory.map { history ->
            val workout = workoutsMap[history.workoutId] ?: throw IllegalStateException("Workout not found")
            WorkoutHistoryResponse(
                name = workout.name,
                imageUrl = workout.imageUrl,
                createdAt = history.createdAt,
                exercisesCount = workout.exercises.count(),
                durationSeconds = history.durationSeconds,
                level = workout.level
            )
        }
    }

    private fun isExerciseMatchFocusArea(exercise: Exercise, focusArea: BodyArea?): Boolean {
        return focusArea == null || exercise.focusArea.contains(focusArea)
    }

    private fun isExerciseMatchUserEquipments(exercise: Exercise, userEquipments: List<GymEquipment>): Boolean {
        return exercise.gymEquipments.isEmpty() || exercise.gymEquipments.all { equipment ->
            userEquipments.contains(equipment)
        }
    }

    private fun Workout.toEntity(): WorkoutEntity {
        val creatorName = if (createdBy == WorkoutCreatedBy.USER) userRepository.findById(creatorId).get().name else ""

        return WorkoutEntity(
            id = id,
            name = name,
            description = description,
            imageUrl = imageUrl,
            level = level,
            creatorName = creatorName,
            exercises = exercises
        )
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

        fun getWorkoutFocusAreas(exercises: List<Exercise>): Set<BodyArea> {
            val focusAreas = mutableSetOf<BodyArea>()
            exercises.forEach { focusAreas.addAll(it.focusArea) }
            return focusAreas
        }
    }
}