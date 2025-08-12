package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.api.dto.GymEquipmentResponse
import com.thechance.evolvefit.api.dto.toGymEquipmentResponse
import com.thechance.evolvefit.service.GymEquipmentsService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/equipments")
@Tag(name = "Equipments", description = "Equipments related APIs")
class EquipmentsController(
    private val gymEquipmentsService: GymEquipmentsService
) {

    @PostMapping("/create")
    fun createEquipment(@RequestParam name: String): ResponseEntity<GymEquipmentResponse> {
        val equipment = gymEquipmentsService.createEquipments(name).toGymEquipmentResponse()
        return ResponseEntity.ok(equipment)
    }

    @DeleteMapping("/delete")
    fun deleteEquipment(@RequestParam id: Long) {
        gymEquipmentsService.deleteEquipment(id)
    }
}