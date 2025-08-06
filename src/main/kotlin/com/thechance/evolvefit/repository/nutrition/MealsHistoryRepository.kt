package com.thechance.evolvefit.repository.nutrition

import com.thechance.evolvefit.service.entity.MealHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MealsHistoryRepository: JpaRepository<MealHistory, UUID> {
    fun findAllByUserId(userId: UUID): List<MealHistory>

    @Query("SELECT SUM(mh.caloriesConsumed) FROM MealHistory mh where mh.userId = :userId")
    fun sumUserCaloriesConsumed(userId: UUID): Int
}