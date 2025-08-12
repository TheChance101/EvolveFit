package com.thechance.evolvefit.api.dto

import com.thechance.evolvefit.service.entity.GymEquipment

data class GymEquipmentResponse(
    val id: Long,
    val name: String
)

fun GymEquipment.toGymEquipmentResponse() = GymEquipmentResponse(id, title)