package com.thechance.evolvefit.api.controller.favorite

import com.thechance.evolvefit.api.dto.favorite.FavoriteWorkoutResponse
import com.thechance.evolvefit.api.dto.favorite.toFavoriteWorkoutResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.favorite.FavoriteWorkout
import com.thechance.evolvefit.service.favorite.FavoriteWorkoutService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/favorite/workout")
@Tag(name = "Favorite Workouts", description = "Favorite Workouts related APIs")
class FavoriteWorkoutController(
    private val favoriteWorkoutService: FavoriteWorkoutService
) {

    @GetMapping
    fun getFavoriteWorkouts(): ResponseEntity<List<FavoriteWorkoutResponse>> {
        val userId = JwtFilter.getUserId()
        val favoriteWorkouts = favoriteWorkoutService.getAllFavoriteWorkouts(userId).map(FavoriteWorkout::toFavoriteWorkoutResponse)
        return ResponseEntity.ok(favoriteWorkouts)
    }

    @PostMapping
    fun addFavoriteWorkout(@RequestParam workoutId: UUID): ResponseEntity<FavoriteWorkoutResponse> {
        val userId = JwtFilter.getUserId()
        val favoriteWorkout = favoriteWorkoutService.addFavoriteWorkout(userId, workoutId).toFavoriteWorkoutResponse()
        return ResponseEntity.ok(favoriteWorkout)
    }

    @DeleteMapping
    fun removeFavoriteWorkout(@RequestParam workoutId: UUID): ResponseEntity<Unit> {
        val userId = JwtFilter.getUserId()
        favoriteWorkoutService.removeFavoriteWorkout(userId, workoutId)
        return ResponseEntity.noContent().build()
    }
}
