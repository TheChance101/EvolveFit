package com.thechance.evolvefit.service.nutrition

import com.thechance.evolvefit.dto.nutrition.AddMealRequest
import com.thechance.evolvefit.dto.nutrition.CaloriesResponse
import com.thechance.evolvefit.entity.Gender
import com.thechance.evolvefit.entity.Goal
import com.thechance.evolvefit.entity.nutrition.MealHistory
import com.thechance.evolvefit.entity.nutrition.MealType
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.repository.nutrition.MealsHistoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class NutritionService(
    private val mealsHistoryRepository: MealsHistoryRepository,
    private val userRepository: UserRepository,
) {

    fun getAllUserMealsHistory(userId: UUID): List<MealHistory> {
        return mealsHistoryRepository.findAllByUserId(userId)
    }

    fun addMeal(userId: UUID, addMealRequest: AddMealRequest): MealHistory {
        val mealHistory = MealHistory(
            userId = userId,
            date = LocalDateTime.now(),
            caloriesConsumed = addMealRequest.caloriesConsumed,
            mealName = addMealRequest.mealName,
            mealType = MealType.valueOf(addMealRequest.mealType)
        )

        return mealsHistoryRepository.save(mealHistory)
    }

    fun deleteMealById(mealId: UUID) = mealsHistoryRepository.deleteById(mealId)

    fun getUserCalories(userId: UUID): CaloriesResponse {
        val caloriesConsumed = mealsHistoryRepository.sumUserCaloriesConsumed(userId)
        val totalCalories = getUserCaloriesNeeded(userId)
        return CaloriesResponse(totalCalories = totalCalories, caloriesConsumed = caloriesConsumed)
    }
    
    private fun getUserCaloriesNeeded(userId: UUID): Int {
        // BMR using Harris-Benedict equation
        val userData = userRepository.findById(userId).orElseThrow()

        val bmr = when (userData.gender) {
            Gender.MALE -> {
                88.362 + (13.397 * userData.weight) + (4.799 * userData.height) - (5.677 * userData.birthday.getUserAge())
            }

            Gender.FEMALE -> {
                447.593 + (9.247 * userData.weight) + (3.098 * userData.height) - (4.330 * userData.birthday.getUserAge())
            }
        }

        // Adjust based on goal
        return when (userData.goal) {
            Goal.LOSE_WEIGHT -> (bmr * 0.8).toInt() // 20% deficit
            Goal.STAY_IN_SHAPE -> bmr.toInt()
            Goal.GAIN_WEIGHT -> (bmr * 1.2).toInt() // 20% surplus
        }
    }

    private fun LocalDate.getUserAge(): Int {
        return LocalDate.now().year - this.year
    }
}