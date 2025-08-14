package com.thechance.evolvefit.config

import com.thechance.evolvefit.config.exceptionHandling.CustomAuthenticationEntryPoint
import com.thechance.evolvefit.service.JwtService
import com.thechance.evolvefit.service.UserProfileService
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val userProfileService: UserProfileService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader = request.getHeader("Authorization")

            val token = authHeader?.takeIf { it.startsWith("Bearer ") }?.removePrefix("Bearer ")?.trim()

            if (token != null && SecurityContextHolder.getContext().authentication == null) {
                val userId = jwtService.extractUserId(token)
                if (!userProfileService.userExists(userId)) throw IllegalStateException("User not found")
                val authentication = UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    emptyList()
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)
        } catch (ex: ExpiredJwtException) {
            authenticationEntryPoint.handleJwtExpiredException(response, ex)
        } catch (ex: Exception) {
            authenticationEntryPoint.handleGeneralException(response, ex)
        }
    }

    companion object {
        fun getUserId(): UUID = SecurityContextHolder.getContext().authentication.principal as UUID
    }
}