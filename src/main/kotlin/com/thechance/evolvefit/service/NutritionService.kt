package com.thechance.evolvefit.service

import com.thechance.evolvefit.api.dto.nutrition.AddMealRequest
import com.thechance.evolvefit.api.dto.nutrition.CaloriesResponse
import com.thechance.evolvefit.api.dto.nutrition.WaterIntakeResponse
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.repository.nutrition.MealsHistoryRepository
import com.thechance.evolvefit.repository.nutrition.WaterIntakeHistoryRepository
import com.thechance.evolvefit.service.entity.*
import com.thechance.evolvefit.service.util.getHeightInCm
import com.thechance.evolvefit.service.util.getWeightInKg
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class NutritionService(
    private val mealsHistoryRepository: MealsHistoryRepository,
    private val waterIntakeHistoryRepository: WaterIntakeHistoryRepository,
    private val userRepository: UserRepository,
) {

    fun getAllUserMealsHistory(userId: UUID, startDate: LocalDateTime, endDate: LocalDateTime): List<MealHistory> {
        return mealsHistoryRepository.findAllByUserIdAndDateBetween(userId, startDate, endDate)
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

    @Transactional
    fun deleteMealById(mealId: UUID, userId: UUID) {
        val deleted = mealsHistoryRepository.deleteByIdAndUserId(mealId, userId) > 0
        if (!deleted) throw IllegalStateException("Meal not found")
    }

    fun getUserCalories(userId: UUID): CaloriesResponse {
        val start = LocalDate.now().atStartOfDay()
        val end = LocalDate.now().plusDays(1).atStartOfDay()
        val caloriesConsumed = mealsHistoryRepository.sumUserCaloriesConsumed(userId, start, end) ?: 0
        val totalCalories = getUserCaloriesNeeded(userId)
        return CaloriesResponse(totalCalories = totalCalories, caloriesConsumed = caloriesConsumed)
    }

    // BMR using Harris-Benedict equation
    private fun getUserCaloriesNeeded(userId: UUID): Int {
        val userData = userRepository.findById(userId).orElseThrow()

        val bmr = when (userData.gender) {
            Gender.MALE -> {
                88.362 + (13.397 * userData.getWeightInKg()) + (4.799 * userData.getHeightInCm()) - (5.677 * userData.birthday.getUserAge())
            }

            Gender.FEMALE -> {
                447.593 + (9.247 * userData.getWeightInKg()) + (3.098 * userData.getHeightInCm()) - (4.330 * userData.birthday.getUserAge())
            }
        }

        return when (userData.goal) {
            Goal.LOSE_WEIGHT -> (bmr * 0.8).toInt()
            Goal.STAY_IN_SHAPE -> bmr.toInt()
            Goal.GAIN_WEIGHT -> (bmr * 1.2).toInt()
        }
    }

    private fun LocalDate.getUserAge(): Int {
        return LocalDate.now().year - this.year
    }

    fun addWaterIntake(userId: UUID, waterIntakeInLitre: Float) {
        val waterIntake = WaterInTakeHistory(
            userId = userId,
            date = LocalDateTime.now(),
            waterAmountInLitre = waterIntakeInLitre
        )

        waterIntakeHistoryRepository.save(waterIntake)
    }

    fun getWaterIntake(userId: UUID): WaterIntakeResponse {
        val start = LocalDate.now().atStartOfDay()
        val end = LocalDate.now().plusDays(1).atStartOfDay()
        val waterConsumed = waterIntakeHistoryRepository.sumUserWaterIntake(userId, start, end) ?: 0.0f
        val totalWater = getTotalWaterNeeded(userId)
        return WaterIntakeResponse(waterConsumed = waterConsumed, totalWater = totalWater)
    }

    private fun getTotalWaterNeeded(userId: UUID): Float {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }
        val baseWaterNeeded = user.getWeightInKg() * 0.033f

        val result = when (user.goal) {
            Goal.LOSE_WEIGHT -> baseWaterNeeded * 1.2f
            Goal.GAIN_WEIGHT -> baseWaterNeeded * 1.1f
            Goal.STAY_IN_SHAPE -> baseWaterNeeded
        }

        return String.format("%.1f", result).toFloat()
    }
}