package com.thechance.evolvefit.service.favorite

import com.thechance.evolvefit.repository.favorite.FavoriteMealRepository
import com.thechance.evolvefit.service.entity.Meal
import com.thechance.evolvefit.service.entity.favorite.FavoriteMeal
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class FavoriteMealService(
    private val favoriteMealRepository: FavoriteMealRepository,
    private val entityManager: EntityManager,
) {

    fun addFavoriteMeal(userId: UUID, mealId: UUID): FavoriteMeal {
        val isExist = favoriteMealRepository.existsFavoriteMealByUserIdAndMealId(userId, mealId)
        if (isExist) throw IllegalStateException("Favorite meal already exists")

        val mealRef = entityManager.getReference(Meal::class.java, mealId)
        val favoriteMeal = FavoriteMeal(
            userId = userId,
            meal = mealRef,
            createdAt = LocalDateTime.now()
        )
        return favoriteMealRepository.save(favoriteMeal)
    }

    fun getAllFavoriteMeals(userId: UUID): List<FavoriteMeal> {
        return favoriteMealRepository.findAllByUserId(userId)
    }

    @Transactional
    fun removeFavoriteMeal(userId: UUID, mealId: UUID) {
        val isDeleted = favoriteMealRepository.removeFavoriteMealByUserIdAndMealId(userId, mealId) > 0
        if (!isDeleted) throw IllegalStateException("Favorite meal not found")
    }
}