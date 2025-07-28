package com.thechance.evolvefit.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.intellij.lang.annotations.Identifier
import java.util.UUID

@Entity
data class User(
    @Identifier
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: UUID = UUID.randomUUID(),
    val userName: String,
    val password: String,
)
