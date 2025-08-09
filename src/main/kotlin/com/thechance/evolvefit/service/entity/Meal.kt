package com.thechance.evolvefit.service.entity

import jakarta.persistence.*
import java.util.*

@Entity
data class Meal(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val calories: Int,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: MealType,
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "meal_ingredients", joinColumns = [JoinColumn(name = "meal_id")])
    @Column(name = "ingredients")
    @Enumerated(EnumType.STRING)
    val ingredients: List<String>,
    var imageUrl: String = ""
)
