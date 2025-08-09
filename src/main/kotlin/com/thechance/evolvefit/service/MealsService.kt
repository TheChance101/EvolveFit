package com.thechance.evolvefit.service

import com.thechance.evolvefit.api.dto.nutrition.CreateMealRequest
import com.thechance.evolvefit.repository.nutrition.MealsRepository
import com.thechance.evolvefit.service.entity.Meal
import org.springframework.stereotype.Service
import java.util.*

@Service
class MealsService(
    private val mealsRepository: MealsRepository
) {

    fun createMeal(createMealRequest: CreateMealRequest): Meal {
        val meal = Meal(
            name = createMealRequest.name,
            description = createMealRequest.description,
            calories = createMealRequest.calories,
            carbs = createMealRequest.carbs,
            protein = createMealRequest.protein,
            fat = createMealRequest.fat,
            type = createMealRequest.type,
            ingredients = createMealRequest.ingredients
        )

        return mealsRepository.save(meal)
    }

    fun getAllMeals(): List<Meal> {
        return mealsRepository.findAll()
    }

    fun getMealById(mealId: UUID): Meal {
        return mealsRepository.findById(mealId).orElseThrow { throw IllegalStateException("Meal not found") }
    }

    fun deleteMeal(mealId: UUID) {
        if (!mealsRepository.existsById(mealId)) throw IllegalStateException("Meal not found")
        mealsRepository.deleteById(mealId)
    }
}