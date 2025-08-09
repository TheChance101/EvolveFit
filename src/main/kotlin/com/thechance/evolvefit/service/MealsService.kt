package com.thechance.evolvefit.service

import com.thechance.evolvefit.api.dto.nutrition.CreateMealRequest
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.repository.nutrition.MealsRepository
import com.thechance.evolvefit.service.entity.Goal
import com.thechance.evolvefit.service.entity.Meal
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class MealsService(
    private val userRepository: UserRepository,
    private val mealsRepository: MealsRepository,
    private val imageService: ImageService,
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

    @Transactional
    fun setMealImage(mealId: UUID, image: MultipartFile): String {
        val meal = mealsRepository.findById(mealId).orElseThrow { throw IllegalStateException("Meal not found") }

        if (meal.imageUrl.isNotBlank()) {
            imageService.deleteImage(meal.imageUrl)
        }

        val imageUrl =  imageService.uploadMealImage(mealId.toString(), image)
        meal.imageUrl = imageUrl
        return imageUrl
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

    fun suggestMealsByUserGoal(userId: UUID): List<Meal> {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }
        return when (user.goal) {
            Goal.LOSE_WEIGHT -> mealsRepository.findAllByCaloriesBetween(0, 500)
            Goal.STAY_IN_SHAPE -> mealsRepository.findAllByCaloriesBetween(500, 700)
            Goal.GAIN_WEIGHT -> mealsRepository.findAllByCaloriesBetween(700, 9999)
        }
    }
}