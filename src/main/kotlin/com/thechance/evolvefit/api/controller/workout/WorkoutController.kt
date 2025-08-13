package com.thechance.evolvefit.api.controller.workout

import com.thechance.evolvefit.api.dto.workout.WorkoutHistoryRequest
import com.thechance.evolvefit.api.dto.workout.WorkoutRequest
import com.thechance.evolvefit.api.dto.workout.WorkoutResponse
import com.thechance.evolvefit.api.dto.workout.toWorkoutResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.workout.CommunityWorkout
import com.thechance.evolvefit.service.entity.workout.Workout
import com.thechance.evolvefit.service.workout.WorkoutService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

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

    @PutMapping("/image")
    fun setWorkoutImage(@RequestParam workoutId: UUID, @RequestParam image: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(workoutService.setWorkoutImage(workoutId, image))
    }

    @GetMapping("/all")
    fun getAllWorkout(): ResponseEntity<List<WorkoutResponse>> {
        val workouts = workoutService.getAllWorkouts().map(Workout::toWorkoutResponse)
        return ResponseEntity.ok(workouts)
    }

    @GetMapping("/community")
    fun getAllCommunityWorkouts(): ResponseEntity<List<WorkoutResponse>> {
        val workouts = workoutService.getAllCommunityWorkouts().map(CommunityWorkout::toWorkoutResponse)
        return ResponseEntity.ok(workouts)
    }

    @GetMapping("/suggested")
    fun suggestWorkoutsForUser(): ResponseEntity<List<WorkoutResponse>> {
        val userId = JwtFilter.getUserId()
        val workouts = workoutService.suggestWorkoutsForUser(userId).map(Workout::toWorkoutResponse)
        return ResponseEntity.ok(workouts)
    }

    @PostMapping("/submit")
    fun submitWorkout(@RequestBody workoutHistoryRequest: WorkoutHistoryRequest): ResponseEntity<Unit> {
        val userId = JwtFilter.getUserId()
        workoutService.submitWorkout(userId, workoutHistoryRequest)
        return ResponseEntity.noContent().build()
    }
}