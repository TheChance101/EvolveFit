package com.thechance.evolvefit.service

import com.thechance.evolvefit.repository.RefreshTokenRepository
import com.thechance.evolvefit.service.entity.RefreshToken
import com.thechance.evolvefit.service.entity.User
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class TokenService(
    private val refreshRepo: RefreshTokenRepository,
) {
    fun createRefreshToken(user: User): RefreshToken {
        val expiry = Instant.now().plus(Duration.ofDays(7)).epochSecond
        val token = UUID.randomUUID().toString()
        val refreshToken = RefreshToken(refreshToken = token, expiresIn = expiry, user = user)
        return refreshRepo.save(refreshToken)
    }

    fun validateRefreshToken(token: String): RefreshToken? {
        val stored = refreshRepo.findByRefreshToken(token) ?: return null
        val expiryDate = Instant.ofEpochSecond(stored.expiresIn)
        return if (expiryDate.isAfter(Instant.now())) stored else null
    }
}