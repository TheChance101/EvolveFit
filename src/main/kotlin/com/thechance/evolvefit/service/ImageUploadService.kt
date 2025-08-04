package com.thechance.evolvefit.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant

@Service
class ImageUploadService(
    private val cloudinary: Cloudinary
) {

    fun upload(file: MultipartFile): String {
        val options = ObjectUtils.asMap(
            "folder", "evolveFit/images",
            "public_id", "custom_filename ${Instant.now().epochSecond}",
            "transformation", "q_auto"
        )
        val uploadResult = cloudinary.uploader().upload(file.bytes, options)

        return uploadResult["secure_url"] as String
    }
}