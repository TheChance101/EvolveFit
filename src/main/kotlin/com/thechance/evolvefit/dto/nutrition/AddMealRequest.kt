package com.thechance.evolvefit.dto.nutrition

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class AddMealRequest(
    @field:Min( 1, message = "caloriesConsumed must be larger than 0")
    val caloriesConsumed: Int,
    @field:NotBlank(message = "mealName must not be blank")
    val mealName: String,
    @field:NotBlank(message = "mealType must not be blank")
    val mealType: String
)
