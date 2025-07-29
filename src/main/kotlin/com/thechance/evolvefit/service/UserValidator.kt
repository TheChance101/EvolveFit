package com.thechance.evolvefit.service

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component

@Component
class UserValidator {

    fun validate(username: String, password: String) {
        if (
            username.isBlank() ||
            password.isBlank() ||
            password.length < 8
            ) {
            throw BadCredentialsException("Invalid username or password")
        }
    }
}