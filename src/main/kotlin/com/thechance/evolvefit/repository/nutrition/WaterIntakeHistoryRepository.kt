package com.thechance.evolvefit.repository.nutrition

import com.thechance.evolvefit.service.entity.WaterInTakeHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface WaterIntakeHistoryRepository: JpaRepository<WaterInTakeHistory, UUID> {
    @Query("SELECT SUM(w.waterAmountInLitre) FROM WaterInTakeHistory w where w.userId = :userId AND w.date >= :start AND w.date < :end")
    fun sumUserWaterIntake(userId: UUID, start: LocalDateTime, end: LocalDateTime): Float?
}