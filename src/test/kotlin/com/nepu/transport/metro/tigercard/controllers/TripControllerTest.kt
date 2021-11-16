package com.nepu.transport.metro.tigercard.controllers

import com.nepu.transport.metro.tigercard.domain.Zone.ZONE_1
import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import com.nepu.transport.metro.tigercard.utils.ObjectMapperUtil.getObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.ZonedDateTime

@SpringBootTest
class TripControllerTest {

    private val mapper = getObjectMapper()
    private lateinit var mockMvc: MockMvc

    @InjectMocks
    private lateinit var tripController: TripController

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).build()
    }

    @Test
    fun `POST should call the endpoint to calculate the fare`() {
        val request = TripFareCalculationRequest(1, ZONE_1, ZONE_1, ZonedDateTime.now())

        mockMvc.perform(post("/api/v1/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk)
    }
}
