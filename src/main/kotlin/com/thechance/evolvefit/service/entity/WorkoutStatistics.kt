package com.thechance.evolvefit.service.entity

import com.thechance.evolvefit.service.entity.workout.BodyArea

data class WorkoutStatistics(
    val totalTimeSpentSeconds: Int,
    val totalWorkouts: Int,
    val workoutsByDay: List<StatisticsByDay>,
    val totalTimeSpentByDay: List<StatisticsByDay>,
    val topFocusAreas: List<FocusAreaStatistics>
) {
    data class StatisticsByDay(
        val day: String,
        val value: Int,
    )

    data class FocusAreaStatistics(
        val area: BodyArea,
        val percentage: Double
    )
}