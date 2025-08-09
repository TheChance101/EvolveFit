package com.thechance.evolvefit.api.controller.nutrition

import com.thechance.evolvefit.api.dto.nutrition.CreateMealRequest
import com.thechance.evolvefit.service.MealsService
import com.thechance.evolvefit.service.entity.Meal
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/meals")
@Tag(name = "Meals", description = "Meals related APIs")
class MealsController(
    private val mealsService: MealsService
) {

    @PostMapping("/create")
    fun createMeal(@Valid @RequestBody createMealRequest: CreateMealRequest): ResponseEntity<Meal> {
        val meal = mealsService.createMeal(createMealRequest)
        return ResponseEntity.ok(meal)
    }

    @GetMapping("/all")
    fun getAllMeals(): ResponseEntity<List<Meal>> {
        return ResponseEntity.ok(mealsService.getAllMeals())
    }

    @DeleteMapping("/delete")
    fun deleteMealById(@RequestParam mealId: UUID): ResponseEntity<Unit> {
        mealsService.deleteMeal(mealId)
        return ResponseEntity.noContent().build()
    }

}