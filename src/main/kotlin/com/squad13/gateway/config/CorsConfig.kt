package com.squad13.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig(private val allowedOriginsEnv: Environment) {

    @Bean
    fun corsFilter(): CorsWebFilter {
        val allowedOrigins = allowedOriginsEnv.getProperty("CORS_ALLOWED_ORIGINS", "*")
            .split(",").map { it.trim() }

        val config = CorsConfiguration().apply {
            allowedOriginPatterns = allowedOrigins
            allowedMethods = listOf(CorsConfiguration.ALL)
            allowedHeaders = listOf(CorsConfiguration.ALL)
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", config)
        }

        return CorsWebFilter(source)
    }
}