package com.thechance.evolvefit.config.exceptionHandling

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val objectMapper = ObjectMapper()

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val apiError = ApiError(
            status = HttpServletResponse.SC_UNAUTHORIZED,
            exception = authException.javaClass.name,
            error = "Unauthorized",
            message = authException.message ?: "Access Denied",
        )

        sendApiErrorResponse(response, apiError)
    }

    fun handleJwtExpiredException(
        response: HttpServletResponse,
        exception: ExpiredJwtException
    ) {
        val apiError = ApiError(
            status = HttpServletResponse.SC_UNAUTHORIZED,
            exception = exception.javaClass.name,
            error = "Unauthorized",
            message = "Jwt Token has expired. Please login again to get a new token.",
        )

        sendApiErrorResponse(response, apiError)
    }

    fun handleGeneralException(
        response: HttpServletResponse,
        exception: Exception
    ) {
        val apiError = ApiError(
            status = HttpServletResponse.SC_UNAUTHORIZED,
            exception = exception.javaClass.name,
            error = "Unauthorized",
            message = exception.message ?: "Access Denied",
        )

        sendApiErrorResponse(response, apiError)
    }

    private fun sendApiErrorResponse(
        response: HttpServletResponse,
        apiError: ApiError
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        response.outputStream.use { os -> objectMapper.writeValue(os, apiError) }
    }
}