package com.thechance.evolvefit.service

import com.thechance.evolvefit.repository.workout.WorkoutHistoryRepository
import com.thechance.evolvefit.service.entity.NutritionStatistics
import com.thechance.evolvefit.service.entity.WorkoutStatistics
import com.thechance.evolvefit.service.entity.workout.BodyArea
import com.thechance.evolvefit.service.entity.workout.WorkoutHistory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class ReportService(
    private val workoutHistoryRepository: WorkoutHistoryRepository,
    private val nutritionService: NutritionService,
) {

    fun getWorkoutReport(userId: UUID, startDate: LocalDateTime, endDate: LocalDateTime): WorkoutStatistics {
        val currentWeekWorkouts = workoutHistoryRepository.findAllWithWorkoutByUserId(userId, startDate, endDate)

        val totalTimeSpent = getTotalTimeSpent(currentWeekWorkouts)
        val totalWorkouts = getTotalWorkouts(currentWeekWorkouts)
        val workoutsByDay = getWorkoutsByDay(currentWeekWorkouts)
        val totalTimeSpentByDay = getTotalTimeSpentByDay(currentWeekWorkouts)
        val topFocusAreas = calculateTopFocusAreas(currentWeekWorkouts)

        return WorkoutStatistics(
            totalTimeSpentSeconds = totalTimeSpent,
            totalWorkouts = totalWorkouts,
            workoutsByDay = workoutsByDay,
            totalTimeSpentByDay = totalTimeSpentByDay,
            topFocusAreas = topFocusAreas
        )
    }

    private fun calculateTopFocusAreas(workoutHistory: List<WorkoutHistory>): List<WorkoutStatistics.FocusAreaStatistics> {
        val focusAreaFrequency = mutableMapOf<BodyArea, Int>()

        workoutHistory.forEach { history ->
            history.workout.exercises.forEach { exercise ->
                exercise.focusArea.forEach { area ->
                    focusAreaFrequency[area] = (focusAreaFrequency[area] ?: 0) + 1
                }
            }
        }

        val totalCount = focusAreaFrequency.values.sum().toDouble()

        return focusAreaFrequency
            .map { (area, count) ->
                WorkoutStatistics.FocusAreaStatistics(
                    area = area,
                    percentage = (count / totalCount) * 100
                )
            }
            .sortedByDescending { it.percentage }
            .take(5)
    }

    private fun getTotalTimeSpent(workoutHistory: List<WorkoutHistory>): Int {
        return workoutHistory.sumOf { it.durationSeconds }
    }

    private fun getTotalWorkouts(workoutHistory: List<WorkoutHistory>): Int {
        return workoutHistory.size
    }

    private fun getWorkoutsByDay(workoutHistory: List<WorkoutHistory>): List<WorkoutStatistics.StatisticsByDay> {
        return workoutHistory
            .groupBy { it.createdAt.dayOfWeek.name }
            .mapValues { it.value.size }
            .map { WorkoutStatistics.StatisticsByDay(it.key, it.value) }
    }

    private fun getTotalTimeSpentByDay(workoutHistory: List<WorkoutHistory>): List<WorkoutStatistics.StatisticsByDay> {
        return workoutHistory
            .groupBy { it.createdAt.dayOfWeek.name }
            .mapValues { workoutsHistoryPerDay ->
                workoutsHistoryPerDay.value.sumOf { workout -> workout.durationSeconds}
            }
            .map { WorkoutStatistics.StatisticsByDay(it.key, it.value) }
    }

    fun getNutritionReport(userId: UUID, startDate: LocalDateTime, endDate: LocalDateTime): NutritionStatistics {
        val waterConsumed = nutritionService.getWaterConsumed(userId, startDate, endDate)
        val caloriesConsumed = nutritionService.getCaloriesConsumed(userId, startDate, endDate)

        val totalDays = ChronoUnit.DAYS.between(startDate, endDate).toInt()
        val totalCalories = nutritionService.getUserCaloriesNeeded(userId) * totalDays

        return NutritionStatistics(
            totalCalories = totalCalories,
            caloriesConsumed = caloriesConsumed,
            waterConsumed = waterConsumed,
        )
    }
}