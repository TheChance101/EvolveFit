package com.thechance.evolvefit.api.dto

import com.thechance.evolvefit.service.entity.Gender
import com.thechance.evolvefit.service.entity.Goal
import com.thechance.evolvefit.service.entity.MeasurementType
import com.thechance.evolvefit.service.entity.WorkoutDays
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class AuthRequest(
    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Invalid email format")
    val email: String,
    @field:NotBlank(message = "password must not be blank")
    @field:Length(min = 8, message = "password must be more than 8 characters")
    val password: String
)

data class AuthResponse(val accessToken: String, val refreshToken: String)

data class RefreshRequest(
    @field:NotBlank(message = "refreshToken must not be blank")
    val refreshToken: String
)

data class CreateUserRequest(
    @field:NotBlank(message = "Email must not be blank")
    @field:Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email format")
    @Schema(description = "User email address", defaultValue = "user@example.com")
    val email: String,
    @field:NotBlank(message = "fullName must not be blank")
    val fullName: String,
    @field:NotBlank(message = "password must not be blank")
    @field:Length(min = 8, message = "password must be more than 8 characters")
    val password: String,
    val birthdate: LocalDate,
    val gender: Gender,
    val measurementType: MeasurementType,
    val height: Float,
    val weight: Float,
    val goal: Goal,
    val workoutDays: List<WorkoutDays>,
    val gymEquipments: List<Long>
)