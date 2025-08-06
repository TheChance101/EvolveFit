package com.thechance.evolvefit.api.controller.nutrition

import com.thechance.evolvefit.api.dto.nutrition.AddMealRequest
import com.thechance.evolvefit.api.dto.nutrition.CaloriesResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.NutritionService
import com.thechance.evolvefit.service.entity.MealHistory
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/nutrition")
class NutritionController(
    private val nutritionService: NutritionService
) {

    @GetMapping("/meals")
    fun getAllUserMealsHistory(): ResponseEntity<List<MealHistory>> {
        val userId = JwtFilter.getUserId()
        return ResponseEntity.ok(nutritionService.getAllUserMealsHistory(userId))
    }

    @PostMapping("/meal")
    fun addMeal(@RequestBody @Valid addMealRequest: AddMealRequest): ResponseEntity<MealHistory> {
        val userId = JwtFilter.getUserId()
        return ResponseEntity.ok(nutritionService.addMeal(userId, addMealRequest))
    }

    @PostMapping("/meal/delete")
    fun deleteMealById(@RequestParam mealId: UUID): ResponseEntity<Unit> {
        nutritionService.deleteMealById(mealId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/calories")
    fun getUserCalories(): ResponseEntity<CaloriesResponse> {
        val userId = JwtFilter.getUserId()
        return ResponseEntity.ok(nutritionService.getUserCalories(userId))
    }
}