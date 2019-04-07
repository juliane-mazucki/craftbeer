package com.beerhouse.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

class SpringMvcConfiguration : WebMvcConfigurerAdapter() {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        // ADD CUSTOM JACKSON CONFIGURATION
        objectMapper.registerModule(KotlinModule())
        objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT)

        return objectMapper
    }
}