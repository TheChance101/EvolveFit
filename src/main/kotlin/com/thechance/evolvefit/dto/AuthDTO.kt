package com.thechance.evolvefit.dto

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val accessToken: String, val refreshToken: String)