package com.thechance.evolvefit.service.entity.report

import java.time.LocalDate

data class WorkoutProgress(
    val workoutDates: Set<LocalDate>,
    val activityPercentage: Float
)