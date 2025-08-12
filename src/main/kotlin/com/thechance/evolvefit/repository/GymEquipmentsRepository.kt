package com.thechance.evolvefit.repository

import com.thechance.evolvefit.service.entity.GymEquipment
import org.springframework.data.jpa.repository.JpaRepository

interface GymEquipmentsRepository: JpaRepository<GymEquipment, Long> {
    fun findByTitle(tittle: String): GymEquipment?
}