package com.thechance.evolvefit.api.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length

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
    @field:Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Invalid email format")
    val email: String,
    @field:NotBlank(message = "fullName must not be blank")
    val fullName: String,
    @field:NotBlank(message = "password must not be blank")
    @field:Length(min = 8, message = "password must be more than 8 characters")
    val password: String,
    val birthdate: String,
    val gender: String,
    val measurementType: String,
    val height: Float,
    val weight: Float,
    val goal: String,
    val workoutDays: List<String>,
    val gymEquipments: List<Long>
)