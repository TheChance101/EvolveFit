package com.thechance.evolvefit.api.controller.favorite

import com.thechance.evolvefit.api.dto.favorite.FavoriteMealResponse
import com.thechance.evolvefit.api.dto.favorite.toFavoriteMealResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.entity.favorite.FavoriteMeal
import com.thechance.evolvefit.service.favorite.FavoriteMealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/favorite/meal")
class FavoriteMealController(
    private val favoriteMealService: FavoriteMealService
) {

    @GetMapping
    fun getFavoriteMeals(): ResponseEntity<List<FavoriteMealResponse>> {
        val userId = JwtFilter.getUserId()
        val favoriteMeals = favoriteMealService.getAllFavoriteMeals(userId).map(FavoriteMeal::toFavoriteMealResponse)
        return ResponseEntity.ok(favoriteMeals)
    }

    @PostMapping
    fun addFavoriteMeal(@RequestParam mealId: UUID): ResponseEntity<FavoriteMealResponse> {
        val userId = JwtFilter.getUserId()
        val favoriteMeal = favoriteMealService.addFavoriteMeal(userId, mealId).toFavoriteMealResponse()
        return ResponseEntity.ok(favoriteMeal)
    }

    @DeleteMapping
    fun removeFavoriteMeal(@RequestParam mealId: UUID): ResponseEntity<Unit> {
        val userId = JwtFilter.getUserId()
        favoriteMealService.removeFavoriteMeal(userId, mealId)
        return ResponseEntity.noContent().build()
    }
}