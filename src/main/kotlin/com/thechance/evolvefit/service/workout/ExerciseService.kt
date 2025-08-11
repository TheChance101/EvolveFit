package com.thechance.evolvefit.service.workout

import com.thechance.evolvefit.api.dto.workout.ExerciseRequest
import com.thechance.evolvefit.repository.GymEquipmentsRepository
import com.thechance.evolvefit.repository.workout.ExerciseRepository
import com.thechance.evolvefit.service.entity.GymEquipment
import com.thechance.evolvefit.service.entity.workout.Exercise
import com.thechance.evolvefit.service.entity.workout.ExerciseType
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ExerciseService(
    private val exerciseRepository: ExerciseRepository,
    private val gymEquipmentsRepository: GymEquipmentsRepository
) {

    fun getAllExercises(): List<Exercise> {
        return exerciseRepository.findAll()
    }

    fun createExercise(userId: UUID, exerciseRequest: ExerciseRequest): Exercise {
        checkExerciseValidation(exerciseRequest)

        val exercise = Exercise(
            name = exerciseRequest.name,
            gymEquipments = exerciseRequest.getGymEquipments(),
            instructions = exerciseRequest.instructions,
            focusArea = exerciseRequest.focusArea,
            exerciseType = exerciseRequest.exerciseType,
            durationSeconds = exerciseRequest.getDuration(),
            reps = exerciseRequest.getReps(),
            createdAt = LocalDateTime.now(),
            creatorId = userId
        )

        return exerciseRepository.save(exercise)
    }

    fun checkExerciseValidation(exerciseRequest: ExerciseRequest) {
        when(exerciseRequest.exerciseType) {
            ExerciseType.DURATION -> if (exerciseRequest.durationSeconds == null) throw IllegalStateException("Duration must be provided")
            ExerciseType.REPS -> if (exerciseRequest.reps == null) throw IllegalStateException("Reps must be provided")
        }
    }

    private fun ExerciseRequest.getGymEquipments(): List<GymEquipment> {
        return if (this.gymEquipments.isNotEmpty()) {
            this.gymEquipments.map { gymEquipmentsRepository.findById(it).get() }
        } else emptyList()
    }

    private fun ExerciseRequest.getDuration(): Int? {
        return if (this.exerciseType == ExerciseType.DURATION) this.durationSeconds else null
    }

    private fun ExerciseRequest.getReps(): Int? {
        return if (this.exerciseType == ExerciseType.REPS) this.reps else null
    }

    fun deleteExercise(id: UUID) {
        if (!exerciseRepository.existsById(id)) throw IllegalStateException("Exercise not found")
        exerciseRepository.deleteById(id)
    }
}