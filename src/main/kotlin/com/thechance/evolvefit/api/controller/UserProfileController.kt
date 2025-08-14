package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.api.dto.profile.UserProfileResponse
import com.thechance.evolvefit.api.dto.profile.toUserProfileResponse
import com.thechance.evolvefit.config.JwtFilter
import com.thechance.evolvefit.service.UserProfileService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/user/profile")
@Tag(name = "User Profile", description = "User Profile related APIs")
class UserProfileController(
    private val imageUploadService: UserProfileService
) {

    @GetMapping
    fun getUserProfile(): ResponseEntity<UserProfileResponse> {
        val userId = JwtFilter.getUserId()
        val userProfile = imageUploadService.getUserProfile(userId).toUserProfileResponse()
        return ResponseEntity.ok(userProfile)
    }

    @PostMapping("/image")
    fun uploadProfileImage(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val userId = JwtFilter.getUserId()
        val imageUrl = imageUploadService.uploadUserProfileImage(userId, file)
        return ResponseEntity.ok(imageUrl)
    }
}