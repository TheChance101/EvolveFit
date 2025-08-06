package com.thechance.evolvefit.service.entity

import jakarta.persistence.*

@Entity
data class RefreshToken(
    @Id @GeneratedValue val id: Long = 0,
    val refreshToken: String,
    val expiresIn: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User
)
