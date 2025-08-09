package com.thechance.evolvefit.repository.nutrition

import com.thechance.evolvefit.service.entity.Meal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MealsRepository: JpaRepository<Meal, UUID> {
}