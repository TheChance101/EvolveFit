package com.thechance.evolvefit.service.entity

import jakarta.persistence.*

@Entity
@Table(name = "equipment")
data class GymEquipment(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val title: String,

    @ManyToMany(mappedBy = "gymEquipments")
    val users: List<User> = listOf()
)
