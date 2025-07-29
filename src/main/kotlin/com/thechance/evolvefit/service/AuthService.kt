package com.thechance.evolvefit.service

import com.thechance.evolvefit.dto.AuthRequest
import com.thechance.evolvefit.dto.AuthResponse
import com.thechance.evolvefit.entity.User
import com.thechance.evolvefit.repository.RefreshTokenRepository
import com.thechance.evolvefit.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepo: UserRepository,
    private val refreshRepo: RefreshTokenRepository,
    private val tokenService: TokenService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val userValidator: UserValidator
) {
    fun signup(request: AuthRequest): AuthResponse {
        userValidator.validate(request.username, request.password)

        userRepo.findByUsername(request.username)?.let {
            throw IllegalStateException("Username already exists")
        }

        val user = User(
            username = request.username.trim(),
            password = passwordEncoder.encode(request.password)
        )
        userRepo.saveAndFlush( user)
        return generateAuthResponse(user)
    }

    fun login(request: AuthRequest): AuthResponse {
        userValidator.validate(request.username, request.password)

        val user = userRepo.findByUsername(request.username)
            ?: throw UsernameNotFoundException("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) throw BadCredentialsException("Invalid")
        return generateAuthResponse(user)
    }

    fun refresh(refreshToken: String): AuthResponse {
        val token = tokenService.validateRefreshToken(refreshToken)
            ?: throw RuntimeException("Invalid or expired refresh token")
        refreshRepo.delete(token)
        return generateAuthResponse(token.user)
    }

    private fun generateAuthResponse(user: User): AuthResponse {
        val accessToken = jwtService.generateToken(user)
        val refreshToken = tokenService.createRefreshToken(user).refreshToken
        return AuthResponse(accessToken, refreshToken)
    }
}