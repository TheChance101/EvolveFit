package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.api.dto.GymEquipmentResponse
import com.thechance.evolvefit.api.dto.toGymEquipmentResponse
import com.thechance.evolvefit.service.GymEquipmentsService
import com.thechance.evolvefit.service.entity.GymEquipment
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
@Tag(name = "Public Endpoints", description = "Public Endpoints related APIs")
class PublicEndPointsController(
    private val gymEquipmentsService: GymEquipmentsService
) {

    @GetMapping("/equipments")
    fun getGymEquipments(): ResponseEntity<List<GymEquipmentResponse>> {
        return ResponseEntity.ok(
            gymEquipmentsService.getAllGymEquipments().map(GymEquipment::toGymEquipmentResponse)
        )
    }
}