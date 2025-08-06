package com.thechance.evolvefit.api.controller

import com.thechance.evolvefit.service.UserProfileService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/user/profile")
class UserProfileController(
    private val imageUploadService: UserProfileService
) {

    @PostMapping("/image")
    fun uploadProfileImage(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val userId = SecurityContextHolder.getContext().authentication.principal as UUID
        val imageUrl = imageUploadService.uploadUserProfileImage(userId, file)
        return ResponseEntity.ok(imageUrl)
    }
}