package com.thechance.evolvefit.config.exceptionHandling

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleValidationException(ex: HttpMessageNotReadableException): ResponseEntity<ApiError> {
        val apiError = ApiError(
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.name,
            error = "Validation Failed",
            message = ex.message,
        )
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ApiError> {
        val errors = ex.bindingResult.allErrors.map {
            when (it) {
                is FieldError -> "${it.field}: ${it.defaultMessage}"
                else -> it.defaultMessage ?: "Validation error"
            }
        }

        val apiError = ApiError(
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.name,
            error = "Validation Failed",
            message = "Request validation failed",
            errors = errors
        )
        return ResponseEntity(apiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    fun handleInvalidRefreshTokenException(ex: InvalidRefreshTokenException): ResponseEntity<ApiError> {
        val apiError = ApiError(
            status = EvolveFitErrorCode.INVALID_REFRESH_TOKEN,
            exception = ex.javaClass.name,
            error = "Invalid refresh token",
            message = ex.message ?: "Invalid refresh token"
        )
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllOtherExceptions(ex: Exception): ResponseEntity<ApiError> {
        val apiError = ApiError(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception = ex.javaClass.name,
            error = "Internal Server Error",
            message = ex.message ?: "Unexpected error occurred"
        )
        return ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
    }


}