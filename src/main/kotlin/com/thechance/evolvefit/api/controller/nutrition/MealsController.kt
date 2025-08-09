package com.thechance.evolvefit.api.controller.nutrition

import com.thechance.evolvefit.api.dto.nutrition.CreateMealRequest
import com.thechance.evolvefit.api.dto.nutrition.MealResponse
import com.thechance.evolvefit.api.dto.nutrition.toMealResponse
import com.thechance.evolvefit.service.MealsService
import com.thechance.evolvefit.service.entity.Meal
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    fun getAllMeals(): ResponseEntity<List<MealResponse>> {
        val meals = mealsService.getAllMeals().map(Meal::toMealResponse)
        return ResponseEntity.ok(meals)
    }

    @DeleteMapping("/delete")
    fun deleteMealById(@RequestParam mealId: UUID): ResponseEntity<Unit> {
        mealsService.deleteMeal(mealId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/get")
    fun getMealById(@RequestParam mealId: UUID): ResponseEntity<Meal> {
        return ResponseEntity.ok(mealsService.getMealById(mealId))
    }

    @PutMapping("/image")
    fun setMealImage(@RequestParam mealId: UUID, @RequestParam image: MultipartFile): ResponseEntity<String> {
        return ResponseEntity.ok(mealsService.setMealImage(mealId, image))
    }
}