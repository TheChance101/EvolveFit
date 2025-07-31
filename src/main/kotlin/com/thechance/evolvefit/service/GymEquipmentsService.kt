package com.thechance.evolvefit.service

import com.thechance.evolvefit.repository.GymEquipmentsRepository
import org.springframework.stereotype.Service

@Service
class GymEquipmentsService(
    private val repository: GymEquipmentsRepository
) {

    fun getAllGymEquipments() = repository.findAll()
}