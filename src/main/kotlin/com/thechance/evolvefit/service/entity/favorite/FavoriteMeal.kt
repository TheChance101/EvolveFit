package com.thechance.evolvefit.service.entity.favorite

import com.thechance.evolvefit.service.entity.Meal
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class FavoriteMeal(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    val meal: Meal,
    val createdAt: LocalDateTime
)
