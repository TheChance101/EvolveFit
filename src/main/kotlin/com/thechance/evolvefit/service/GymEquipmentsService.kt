package com.thechance.evolvefit.service

import com.thechance.evolvefit.repository.GymEquipmentsRepository
import com.thechance.evolvefit.service.entity.GymEquipment
import org.springframework.stereotype.Service

@Service
class GymEquipmentsService(
    private val repository: GymEquipmentsRepository
) {

    fun createEquipments(title: String): GymEquipment {
        if (repository.findByTitle(title) != null) throw IllegalStateException("Equipment already exists")
        return repository.save(GymEquipment(title = title))
    }

    fun deleteEquipment(id: Long) {
        if (!repository.existsById(id)) throw IllegalStateException("Equipment not found")
        repository.deleteById(id)
    }

    fun getAllGymEquipments() = repository.findAll()
}