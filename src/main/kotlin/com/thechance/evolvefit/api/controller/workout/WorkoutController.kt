package com.thechance.evolvefit.api.controller.workout

import com.thechance.evolvefit.api.dto.workout.*
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.workout.BodyArea
import com.thechance.evolvefit.service.entity.workout.WorkoutEntity
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
    fun createWorkout(@RequestBody workoutRequest: WorkoutRequest): ResponseEntity<WorkoutDetailsResponse> {
        val userId = JwtFilter.Companion.getUserId()
        val workout = workoutService.createWorkout(userId, workoutRequest).toWorkoutDetailsResponse()
        return ResponseEntity.ok(workout)
    }

    @PutMapping("/image")
    fun setWorkoutImage(@RequestParam workoutId: UUID, @RequestParam image: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(workoutService.setWorkoutImage(workoutId, image))
    }

    @GetMapping("/community")
    fun getAllCommunityWorkouts(@RequestParam focusArea: BodyArea? = null): ResponseEntity<List<WorkoutResponse>> {
        val workouts = workoutService.getAllCommunityWorkouts(focusArea).map(WorkoutEntity::toWorkoutResponse)
        return ResponseEntity.ok(workouts)
    }

    @GetMapping("/suggested")
    fun suggestWorkoutsForUser(@RequestParam focusArea: BodyArea? = null): ResponseEntity<List<WorkoutResponse>> {
        val userId = JwtFilter.getUserId()
        val workouts = workoutService.suggestWorkoutsForUser(userId, focusArea).map(WorkoutEntity::toWorkoutResponse)
        return ResponseEntity.ok(workouts)
    }

    @GetMapping("/details")
    fun getWorkoutDetails(@RequestParam workoutId: UUID): ResponseEntity<WorkoutDetailsResponse> {
        val workout = workoutService.getWorkoutDetails(workoutId).toWorkoutDetailsResponse()
        return ResponseEntity.ok(workout)
    }

    @PostMapping("/submit")
    fun submitWorkout(@RequestBody workoutHistoryRequest: WorkoutHistoryRequest): ResponseEntity<Unit> {
        val userId = JwtFilter.getUserId()
        workoutService.submitWorkout(userId, workoutHistoryRequest)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/history")
    fun getUserWorkoutsHistory(): ResponseEntity<List<WorkoutHistoryResponse>> {
        val userId = JwtFilter.getUserId()
        val workoutsHistory = workoutService.getUserWorkoutsHistory(userId)
        return ResponseEntity.ok(workoutsHistory)
    }
}