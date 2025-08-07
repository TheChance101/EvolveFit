package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.service.GymEquipmentsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
class GymEquipmentsController(
    private val gymEquipmentsService: GymEquipmentsService
) {

    @GetMapping("/equipments")
    fun getGymEquipments(): ResponseEntity<List<GymEquipmentsResponse>> {
        return ResponseEntity.ok(
            gymEquipmentsService.getAllGymEquipments().map { GymEquipmentsResponse(it.id, it.title) }
        )
    }
}

data class GymEquipmentsResponse(val id: Long, val name: String)