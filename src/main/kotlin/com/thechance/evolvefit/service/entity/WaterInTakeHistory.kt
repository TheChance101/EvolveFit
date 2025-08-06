package com.thechance.evolvefit.service.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
data class WaterInTakeHistory(
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    val date: LocalDateTime,
    @Column(nullable = false)
    val waterAmountInLitre: Float
)
