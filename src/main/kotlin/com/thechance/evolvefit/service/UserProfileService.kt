package com.thechance.evolvefit.service

import com.thechance.evolvefit.api.dto.profile.EditProfileRequest
import com.thechance.evolvefit.repository.GymEquipmentsRepository
import com.thechance.evolvefit.repository.UserRepository
import com.thechance.evolvefit.service.entity.User
import com.thechance.evolvefit.service.util.getUserHeight
import com.thechance.evolvefit.service.util.getUserWeight
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UserProfileService(
    private val userRepository: UserRepository,
    private val imageService: ImageService,
    private val gymEquipmentsRepository: GymEquipmentsRepository,
) {

    fun getUserProfile(userId: UUID): User {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }
        return user
    }

    fun editUserProfile(userId: UUID, editProfileRequest: EditProfileRequest): User {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }

        val updatedUser = user.copy(
            birthday = editProfileRequest.birthDate,
            gender = editProfileRequest.gender,
            measurementType = editProfileRequest.measurementType,
            height = getUserHeight(editProfileRequest.height, editProfileRequest.measurementType),
            weight = getUserWeight(editProfileRequest.weight, editProfileRequest.measurementType),
            goal = editProfileRequest.goal,
            workoutDays = editProfileRequest.workoutDays,
            gymEquipments = if (editProfileRequest.gymEquipments.isNotEmpty()) {
                editProfileRequest.gymEquipments.map { gymEquipmentsRepository.findById(it).get() }
            } else emptyList(),
        )

        return userRepository.save(updatedUser)
    }

    @Transactional
    fun uploadUserProfileImage(userId: UUID, file: MultipartFile): String {
        val user = userRepository.findById(userId).orElseThrow { throw IllegalStateException("User not found") }

        if (user.imageUrl.isNotBlank()) {
            imageService.deleteImage(user.imageUrl)
        }

        val imageUrl = imageService.uploadUserProfile(userId.toString(), file)
        user.imageUrl = imageUrl
        return imageUrl
    }

    fun userExists(userId: UUID): Boolean {
        return userRepository.existsById(userId)
    }
}