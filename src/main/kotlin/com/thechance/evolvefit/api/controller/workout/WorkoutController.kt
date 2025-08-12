package com.thechance.evolvefit.api.controller.workout

import com.thechance.evolvefit.api.dto.workout.WorkoutRequest
import com.thechance.evolvefit.api.dto.workout.WorkoutResponse
import com.thechance.evolvefit.api.dto.workout.toWorkoutResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.workout.Workout
import com.thechance.evolvefit.service.workout.WorkoutService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/workout")
@Tag(name = "Workouts", description = "Workouts related APIs")
class WorkoutController(
    private val workoutService: WorkoutService
) {

    @PostMapping("/create")
    fun createWorkout(@RequestBody workoutRequest: WorkoutRequest): ResponseEntity<WorkoutResponse> {
        val userId = JwtFilter.Companion.getUserId()
        val workout = workoutService.createWorkout(userId, workoutRequest).toWorkoutResponse()
        return ResponseEntity.ok(workout)
    }

    @GetMapping("/all")
    fun getAllWorkout(): ResponseEntity<List<WorkoutResponse>> {
        val workouts = workoutService.getAllWorkouts().map(Workout::toWorkoutResponse)
        return ResponseEntity.ok(workouts)
    }
}