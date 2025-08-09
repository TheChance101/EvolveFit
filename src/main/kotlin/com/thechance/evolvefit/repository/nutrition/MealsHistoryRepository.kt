package com.thechance.evolvefit.repository.nutrition

import com.thechance.evolvefit.service.entity.MealHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface MealsHistoryRepository: JpaRepository<MealHistory, UUID> {
    fun findAllByUserIdAndDateBetween(userId: UUID, start: LocalDateTime, end: LocalDateTime): List<MealHistory>

    @Query("SELECT SUM(mh.caloriesConsumed) FROM MealHistory mh where mh.userId = :userId AND mh.date >= :start AND mh.date < :end")
    fun sumUserCaloriesConsumed(userId: UUID, start: LocalDateTime, end: LocalDateTime): Int?

    fun deleteByIdAndUserId(id: UUID, userId: UUID): Int
}