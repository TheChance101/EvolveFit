package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.ReportService
import com.thechance.evolvefit.service.entity.NutritionStatistics
import com.thechance.evolvefit.service.entity.WorkoutStatistics
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/report")
@Tag(name = "Report", description = "Report related APIs")
class ReportController(
    private val reportService: ReportService
) {

    @GetMapping("/workout")
    fun getWorkoutReport(
        @RequestParam startDate: LocalDateTime,
        @RequestParam endDate: LocalDateTime,
    ): ResponseEntity<WorkoutStatistics> {
        val userId = JwtFilter.getUserId()
        val report = reportService.getWorkoutReport(userId, startDate, endDate)
        return ResponseEntity.ok(report)
    }

    @GetMapping("/nutrition")
    fun getNutritionReport(
        @RequestParam startDate: LocalDateTime,
        @RequestParam endDate: LocalDateTime,
    ): ResponseEntity<NutritionStatistics> {
        val userId = JwtFilter.getUserId()
        val report = reportService.getNutritionReport(userId, startDate, endDate)
        return ResponseEntity.ok(report)
    }
}