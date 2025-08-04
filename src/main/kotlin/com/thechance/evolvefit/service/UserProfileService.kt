package com.thechance.evolvefit.service

import com.thechance.evolvefit.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UserProfileService(
    private val userRepository: UserRepository,
    private val imageService: ImageService
) {

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
}