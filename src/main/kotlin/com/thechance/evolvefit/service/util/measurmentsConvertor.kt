package com.thechance.evolvefit.service.util

import com.thechance.evolvefit.service.entity.MeasurementType
import com.thechance.evolvefit.service.entity.User

fun getUserHeight(height: Float, measurementType: MeasurementType): Float = when (measurementType) {
    MeasurementType.METRIC -> height
    MeasurementType.IMPERIAL -> convertFeetToCm(height)
}

fun getUserWeight(weight: Float, measurementType: MeasurementType): Float = when (measurementType) {
    MeasurementType.METRIC -> weight
    MeasurementType.IMPERIAL -> convertPoundsToKg(weight)
}

private fun convertFeetToCm(feet: Float): Float = feet * 30.48f

private fun convertPoundsToKg(pounds: Float): Float = pounds * 0.453592f

fun User.getWeightInKg() = getUserWeight(weight, measurementType)

fun User.getHeightInCm() = getUserHeight(height, measurementType)