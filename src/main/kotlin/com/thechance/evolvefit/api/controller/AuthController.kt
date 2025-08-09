package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.api.dto.AuthRequest
import com.thechance.evolvefit.api.dto.AuthResponse
import com.thechance.evolvefit.api.dto.CreateUserRequest
import com.thechance.evolvefit.api.dto.RefreshRequest
import com.thechance.evolvefit.service.AuthService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication related APIs")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.signup(request))

    @PostMapping("/login")
    fun login(@RequestBody @Valid request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))

    @PostMapping("/refresh")
    fun refresh(@RequestBody @Valid request: RefreshRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.refresh(request.refreshToken))
    }
}