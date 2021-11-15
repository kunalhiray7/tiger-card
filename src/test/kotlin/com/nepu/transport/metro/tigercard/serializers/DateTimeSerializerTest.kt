package com.nepu.transport.metro.tigercard.serializers

import com.affinidi.pgsel.migrationservice.utils.ObjectMapperUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.ZoneOffset
import java.time.ZonedDateTime

class DateTimeSerializerTest {

    private val mapper = ObjectMapperUtil.getObjectMapper()

    @Test
    fun `should serialize into ZonedDateTime`() {
        assertEquals(""""2020-12-31T12:59:59Z"""", mapper.writeValueAsString(ZonedDateTime.of(2020, 12, 31, 12, 59, 59, 0, ZoneOffset.UTC)))
    }
}
