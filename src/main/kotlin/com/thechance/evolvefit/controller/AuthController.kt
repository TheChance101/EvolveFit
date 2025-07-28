package com.thechance.evolvefit.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController {

    @PostMapping("/signup")
    fun signup() {

    }

    @PostMapping("/login")
    fun login(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}