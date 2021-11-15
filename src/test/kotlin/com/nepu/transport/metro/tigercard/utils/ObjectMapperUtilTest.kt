package com.nepu.transport.metro.tigercard.utils

import com.affinidi.pgsel.migrationservice.utils.ObjectMapperUtil.getObjectMapper
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ObjectMapperUtilTest {

    @Test
    fun `objectMapper should have Kotlin module`() {
        val objectMapper = getObjectMapper()

        val registeredModuleIds = objectMapper.registeredModuleIds

        assertTrue(registeredModuleIds.contains("com.fasterxml.jackson.module.kotlin.KotlinModule"))
    }

    @Test
    fun `objectMapper should have Java Time module`() {
        val objectMapper = getObjectMapper()

        val registeredModuleIds = objectMapper.registeredModuleIds

        assertTrue(registeredModuleIds.contains("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule"))
    }
}
