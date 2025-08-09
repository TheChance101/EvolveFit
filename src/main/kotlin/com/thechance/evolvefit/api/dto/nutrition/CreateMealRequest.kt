package com.thechance.evolvefit.api.dto.nutrition

import com.thechance.evolvefit.service.entity.MealType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class CreateMealRequest(
    @field:NotBlank(message = "mealName must not be blank")
    val name: String,
    val description: String = "",
    @field:Min( 1, message = "calories must be larger than 0")
    val calories: Int,
    @field:Min( 1, message = "carbs must be larger than 0")
    val carbs: Int,
    @field:Min( 1, message = "protein must be larger than 0")
    val protein: Int,
    @field:Min( 1, message = "fat must be larger than 0")
    val fat: Int,
    val type: MealType,
    val ingredients: List<String>,
)
