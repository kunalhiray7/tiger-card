package com.affinidi.pgsel.migrationservice.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.nepu.transport.metro.tigercard.serializers.DateTimeDeserializer
import com.nepu.transport.metro.tigercard.serializers.DateTimeSerializer
import com.fasterxml.jackson.databind.SerializationFeature
import java.time.ZonedDateTime

object ObjectMapperUtil {

    fun getObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(ZonedDateTime::class.java, DateTimeSerializer())
        javaTimeModule.addDeserializer(ZonedDateTime::class.java, DateTimeDeserializer())

        objectMapper.registerModule(KotlinModule())
        objectMapper.registerModule(javaTimeModule)

        return objectMapper
    }

}
