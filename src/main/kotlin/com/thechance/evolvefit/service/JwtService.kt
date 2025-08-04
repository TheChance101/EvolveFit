package com.thechance.evolvefit.service

import com.thechance.evolvefit.entity.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    private val accessExpiration = Duration.ofHours(1)

    @Value("\${jwt.secret-key}")
    private lateinit var secret: String
    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        val decodedKey = Base64.getDecoder().decode(secret)
        secretKey = Keys.hmacShaKeyFor(decodedKey)
    }

    fun generateToken(user: User): String =
        Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(Date())
            .setExpiration(Date.from(Instant.now().plus(accessExpiration)))
            .signWith(secretKey)
            .compact()

    fun extractUserId(token: String): UUID {
        val subject = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject

        return UUID.fromString(subject)
    }
}