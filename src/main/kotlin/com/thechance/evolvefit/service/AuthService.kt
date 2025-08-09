package com.thechance.evolvefit.service

import com.thechance.evolvefit.api.dto.AuthRequest
import com.thechance.evolvefit.api.dto.AuthResponse
import com.thechance.evolvefit.api.dto.CreateUserRequest
import com.thechance.evolvefit.repository.GymEquipmentsRepository
import com.thechance.evolvefit.repository.RefreshTokenRepository
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.service.entity.*
import com.thechance.evolvefit.service.util.getUserHeight
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AuthService(
    private val userRepo: UserRepository,
    private val refreshRepo: RefreshTokenRepository,
    private val tokenService: TokenService,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val gymEquipmentsRepository: GymEquipmentsRepository
) {
    fun signup(request: CreateUserRequest): AuthResponse {
        userRepo.findByEmail(request.email)?.let { throw IllegalStateException("User already exists") }
        val measurementType = MeasurementType.valueOf(request.measurementType)

        val user = User(
            name = request.fullName.trim(),
            email = request.email.trim(),
            password = passwordEncoder.encode(request.password),
            birthday = LocalDate.parse(request.birthdate),
            gender = Gender.valueOf(request.gender),
            measurementType = measurementType,
            height = getUserHeight(request.height, measurementType),
            weight = getUserHeight(request.weight, measurementType),
            goal = Goal.valueOf(request.goal),
            workoutDays = request.workoutDays.map { WorkoutDays.valueOf(it) }.toSet(),
            gymEquipments = if (request.gymEquipments.isNotEmpty()) {
                request.gymEquipments.map { gymEquipmentsRepository.findById(it).get() }
            } else emptyList()
        )
        userRepo.saveAndFlush(user)
        return generateAuthResponse(user)
    }

    fun login(request: AuthRequest): AuthResponse {
        val user = userRepo.findByEmail(request.email) ?: throw IllegalStateException("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw BadCredentialsException("Invalid")
        }
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