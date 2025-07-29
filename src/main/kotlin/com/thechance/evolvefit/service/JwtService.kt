package com.thechance.evolvefit.service

import com.thechance.evolvefit.entity.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.Date

@Service
class JwtService {
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val accessExpiration = Duration.ofHours(1)

    fun generateToken(user: User): String =
        Jwts.builder()
            .setSubject(user.username)
            .setIssuedAt(Date())
            .setExpiration(Date.from(Instant.now().plus(accessExpiration)))
            .signWith(secretKey)
            .compact()

    fun extractUsername(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

    fun isTokenValid(token: String, user: User): Boolean {
        val username = extractUsername(token)
        return username == user.username
    }
}