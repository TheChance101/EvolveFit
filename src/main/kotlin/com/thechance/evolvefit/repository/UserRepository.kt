package com.thechance.evolvefit.repository

import com.thechance.evolvefit.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?
}