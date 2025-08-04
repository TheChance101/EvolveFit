package com.thechance.evolvefit.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant

@Service
class ImageService(
    private val cloudinary: Cloudinary
) {

    fun uploadUserProfile(fileName: String, file: MultipartFile): String {
        val options = ObjectUtils.asMap(
            "folder", "evolveFit/images/profile",
            "public_id", "$fileName-${Instant.now().epochSecond}",
            "transformation", "q_auto"
        )
        val uploadResult = cloudinary.uploader().upload(file.bytes, options)

        return uploadResult["secure_url"] as String
    }

    fun deleteImage(url: String) {
        val publicId = extractPublicId(url)
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())
    }

    private fun extractPublicId(url: String): String {
        val baseUrl = url.substringAfter("/upload/").substringBeforeLast(".")
        return baseUrl.substringAfter("/") // removes version prefix
    }
}