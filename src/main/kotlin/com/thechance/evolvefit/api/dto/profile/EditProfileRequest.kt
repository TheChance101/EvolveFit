package com.thechance.evolvefit.api.dto.profile

import com.thechance.evolvefit.service.entity.Gender
import com.thechance.evolvefit.service.entity.Goal
import com.thechance.evolvefit.service.entity.MeasurementType
import com.thechance.evolvefit.service.entity.WorkoutDays
import jakarta.validation.constraints.Min
import java.time.LocalDate

data class EditProfileRequest(
    val birthDate: LocalDate,
    val gender: Gender,
    val measurementType: MeasurementType,
    @field:Min(1, message = "height must be larger than 0")
    val height: Float,
    @field:Min(1, message = "weight must be larger than 0")
    val weight: Float,
    val goal: Goal,
    val gymEquipments: List<Long>,
    val workoutDays: Set<WorkoutDays>
)
