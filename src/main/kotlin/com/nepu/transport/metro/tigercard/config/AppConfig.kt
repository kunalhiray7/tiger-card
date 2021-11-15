package com.nepu.transport.metro.tigercard.config

import com.affinidi.pgsel.migrationservice.utils.ObjectMapperUtil
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapperUtil.getObjectMapper()
}
