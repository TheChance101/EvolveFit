package com.thechance.evolvefit.controller

import com.thechance.evolvefit.dto.AuthRequest
import com.thechance.evolvefit.dto.AuthResponse
import com.thechance.evolvefit.dto.CreateUserRequest
import com.thechance.evolvefit.dto.RefreshRequest
import com.thechance.evolvefit.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: CreateUserRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.signup(request))

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.refresh(request.refreshToken))
    }
}