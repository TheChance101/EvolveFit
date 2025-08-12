package com.thechance.evolvefit.api.controller.workout

import com.thechance.evolvefit.api.dto.workout.ExerciseRequest
import com.thechance.evolvefit.api.dto.workout.ExerciseResponse
import com.thechance.evolvefit.api.dto.workout.toExerciseResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.workout.Exercise
import com.thechance.evolvefit.service.workout.ExerciseService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/exercise")
@Tag(name = "Exercises", description = "Exercises related APIs")
class ExerciseController(
    private val exerciseService: ExerciseService,
) {

    @PostMapping("/create")
    fun createExercise(@Valid @RequestBody exerciseRequest: ExerciseRequest): ResponseEntity<ExerciseResponse> {
        val userId = JwtFilter.getUserId()
        val exercise = exerciseService.createExercise(userId, exerciseRequest).toExerciseResponse()
        return ResponseEntity(exercise, HttpStatus.CREATED)
    }

    @PutMapping("/image")
    fun setExerciseImage(@RequestParam exerciseId: UUID, @RequestParam image: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(exerciseService.setExerciseImage(exerciseId, image))
    }

    @GetMapping("/all")
    fun getAllExercises(): ResponseEntity<List<ExerciseResponse>> {
        val exercises = exerciseService.getAllExercises().map(Exercise::toExerciseResponse)
        return ResponseEntity.ok(exercises)
    }

    @DeleteMapping("/delete")
    fun deleteExercise(@RequestParam exerciseId: UUID): ResponseEntity<Unit> {
        exerciseService.deleteExercise(exerciseId)
        return ResponseEntity.noContent().build()
    }
}