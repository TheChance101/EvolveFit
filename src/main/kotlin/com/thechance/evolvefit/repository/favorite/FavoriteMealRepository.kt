package com.thechance.evolvefit.repository.favorite

import com.thechance.evolvefit.service.entity.favorite.FavoriteMeal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoriteMealRepository: JpaRepository<FavoriteMeal, UUID> {
    fun findAllByUserId(userId: UUID): List<FavoriteMeal>
    fun removeFavoriteMealByUserIdAndMealId(userId: UUID, mealId: UUID): Int
    fun existsFavoriteMealByUserIdAndMealId(userId: UUID, mealId: UUID): Boolean
}