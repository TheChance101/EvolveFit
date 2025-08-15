package com.thechance.evolvefit.repository.workout

import com.thechance.evolvefit.service.entity.workout.WorkoutHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface WorkoutHistoryRepository: JpaRepository<WorkoutHistory, UUID> {
    @Query("""
        SELECT h FROM WorkoutHistory h
        JOIN FETCH h.workout
        WHERE h.userId = :userId
        ORDER BY h.createdAt DESC
    """)
    fun findAllWithWorkoutByUserId(userId: UUID): List<WorkoutHistory>

    @Query("""
        SELECT h FROM WorkoutHistory h
        JOIN FETCH h.workout
        WHERE h.userId = :userId AND h.createdAt >= :startDate AND h.createdAt < :endDate
    """)
    fun findAllWithWorkoutByUserId(userId: UUID, startDate: LocalDateTime, endDate: LocalDateTime): List<WorkoutHistory>
}