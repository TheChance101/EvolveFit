package com.thechance.evolvefit.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<String> {
        val response = ex.localizedMessage ?: "Something went wrong"
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}