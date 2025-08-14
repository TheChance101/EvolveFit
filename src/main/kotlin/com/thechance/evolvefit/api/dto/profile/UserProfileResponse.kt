package com.thechance.evolvefit.api.dto.profile

import com.thechance.evolvefit.api.dto.GymEquipmentResponse
import com.thechance.evolvefit.api.dto.toGymEquipmentResponse
import com.thechance.evolvefit.service.entity.*

data class UserProfileResponse(
    val name: String,
    val email: String,
    val birthDate: String,
    val gender: Gender,
    val imageUrl: String,
    val measurementType: MeasurementType,
    val height: Float,
    val weight: Float,
    val goal: Goal,
    val gymEquipments: List<GymEquipmentResponse>,
    val workoutDays: Set<WorkoutDays>
)

fun User.toUserProfileResponse() = UserProfileResponse(
    name = name,
    email = email,
    birthDate = birthday.toString(),
    gender = gender,
    imageUrl = imageUrl,
    measurementType = measurementType,
    height = height,
    weight = weight,
    goal = goal,
    gymEquipments = gymEquipments.map(GymEquipment::toGymEquipmentResponse),
    workoutDays = workoutDays
)
