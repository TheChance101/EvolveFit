package com.thechance.evolvefit.config

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConfigurationProperties(prefix = "cloudinary")
data class CloudinaryProperties(
    val cloudName: String,
    val apiKey: String,
    val apiSecret: String
)

@Configuration
@EnableConfigurationProperties(CloudinaryProperties::class)
class CloudinaryConfig {

    @Bean
    fun cloudinary(cloudinaryProperties: CloudinaryProperties): Cloudinary {
        return Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", cloudinaryProperties.cloudName,
                "api_key", cloudinaryProperties.apiKey,
                "api_secret", cloudinaryProperties.apiSecret
            )
        )
    }
}