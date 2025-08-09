package com.thechance.evolvefit.api.dto.nutrition

import com.thechance.evolvefit.service.entity.Meal
import com.thechance.evolvefit.service.entity.MealType
import java.util.*

data class MealResponse(
    val id: UUID,
    val name: String,
    val type: MealType,
    val calories: Int,
    val imageUrl: String = ""
)

fun Meal.toMealResponse() = MealResponse(
    id = id,
    name = name,
    type = type,
    calories = calories,
    imageUrl = imageUrl
)