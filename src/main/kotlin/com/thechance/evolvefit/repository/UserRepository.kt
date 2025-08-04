package com.thechance.evolvefit.repository

import com.thechance.evolvefit.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}