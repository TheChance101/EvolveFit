package com.thechance.evolvefit.api.controller.nutrition

import com.thechance.evolvefit.api.dto.nutrition.AddMealRequest
import com.thechance.evolvefit.api.dto.nutrition.CaloriesResponse
import com.thechance.evolvefit.api.dto.nutrition.WaterIntakeResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.NutritionService
import com.thechance.evolvefit.service.entity.MealHistory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/nutrition")
@Tag(name = "Nutrition", description = "Nutrition related APIs")
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

    @PostMapping("/water")
    @Operation(
        summary = "Add water intake",
        description = "Records the amount of water consumed by the user"
    )
    @Parameter(name = "waterIntake", description = "Amount of water consumed in litres")
    fun addWaterIntake(@RequestParam waterIntake: Float): ResponseEntity<Unit> {
        nutritionService.addWaterIntake(JwtFilter.getUserId(), waterIntake)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/water")
    fun getWaterIntake(): ResponseEntity<WaterIntakeResponse> {
        val waterIntake = nutritionService.getWaterIntake(JwtFilter.getUserId())
        return ResponseEntity.ok(WaterIntakeResponse(waterIntake))
    }
}