package com.thechance.evolvefit.api.dto.nutrition

data class MealsGroupResponse (
    val breakfast: Int,
    val lunch: Int,
    val dinner: Int,
    val snack: Int,
    val remainingCalories: Int
)