package com.thechance.evolvefit.service.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class MealHistory(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    val date: LocalDateTime,
    @Column(nullable = false)
    val caloriesConsumed: Int,
    @Column(nullable = false)
    val mealName: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val mealType: MealType
)

enum class MealType {
    BREAKFAST, LUNCH, DINNER, SNACK
}