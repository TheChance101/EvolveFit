package com.thechance.evolvefit.dto

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val accessToken: String, val refreshToken: String)

data class CreateUserRequest(
    val username: String,
    val password: String,
    val birthdate: String,
    val gender: String,
    val measurementType: String,
    val height: Float,
    val weight: Float,
    val goal: String,
    val tools: List<String>,
    val workoutDays: List<String>,
)