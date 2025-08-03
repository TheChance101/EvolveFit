package com.thechance.evolvefit.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageUploadService(
    private val cloudinary: Cloudinary
) {

    fun upload(file: MultipartFile): String {
        val options = ObjectUtils.asMap(
            "folder", "my_app/images",
            "public_id", "custom_filename"
        )
        val uploadResult = cloudinary.uploader().upload(file.bytes, options)

        return uploadResult["secure_url"] as String
    }
}