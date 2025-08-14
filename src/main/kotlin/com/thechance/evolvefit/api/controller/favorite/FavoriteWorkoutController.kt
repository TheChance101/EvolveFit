package com.thechance.evolvefit.api.controller.favorite

import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.favorite.FavoriteWorkout
import com.thechance.evolvefit.service.favorite.FavoriteWorkoutService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/favorite/workout")
class FavoriteWorkoutController(
    private val favoriteWorkoutService: FavoriteWorkoutService
) {

    @GetMapping
    fun getFavoriteWorkouts(): ResponseEntity<List<FavoriteWorkout>> {
        val userId = JwtFilter.getUserId()
        val favoriteWorkouts = favoriteWorkoutService.getAllFavoriteWorkouts(userId)
        return ResponseEntity.ok(favoriteWorkouts)
    }

    @PostMapping
    fun addFavoriteWorkout(@RequestParam workoutId: UUID): ResponseEntity<FavoriteWorkout> {
        val userId = JwtFilter.getUserId()
        val favoriteWorkout = favoriteWorkoutService.addFavoriteWorkout(userId, workoutId)
        return ResponseEntity.ok(favoriteWorkout)
    }

    @DeleteMapping
    fun removeFavoriteWorkout(@RequestParam workoutId: UUID): ResponseEntity<Unit> {
        val userId = JwtFilter.getUserId()
        favoriteWorkoutService.removeFavoriteWorkout(userId, workoutId)
        return ResponseEntity.noContent().build()
    }
}
