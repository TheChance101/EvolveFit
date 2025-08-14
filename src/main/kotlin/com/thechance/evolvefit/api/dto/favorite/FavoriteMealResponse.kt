package com.thechance.evolvefit.api.dto.favorite

import com.thechance.evolvefit.service.entity.MealType
import com.thechance.evolvefit.service.entity.favorite.FavoriteMeal
import java.util.*

data class FavoriteMealResponse(
    val id: UUID,
    val name: String,
    val mealType: MealType,
    val calories: Int,
    val imageUrl: String,
)

fun FavoriteMeal.toFavoriteMealResponse() = FavoriteMealResponse(
    id = id,
    name = meal.name,
    mealType = meal.type,
    calories = meal.calories,
    imageUrl = meal.imageUrl,
)
