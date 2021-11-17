package com.nepu.transport.metro.tigercard.controllers

import com.nepu.transport.metro.tigercard.controllers.MessageConverter.jacksonDateTimeConverter
import com.nepu.transport.metro.tigercard.domain.Zone.ZONE_1
import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import com.nepu.transport.metro.tigercard.dtos.TripsFareCalculationResponse
import com.nepu.transport.metro.tigercard.services.TripService
import com.nepu.transport.metro.tigercard.utils.ObjectMapperUtil.getObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.ZoneId
import java.time.ZonedDateTime

@SpringBootTest
class TripControllerTest {

    private val mapper = getObjectMapper()
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var tripService: TripService

    @InjectMocks
    private lateinit var tripController: TripController

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).setMessageConverters(jacksonDateTimeConverter()).build()
    }

    @Test
    fun `POST should call the endpoint to calculate the fare`() {
        val request = listOf(TripFareCalculationRequest(1, ZONE_1, ZONE_1, ZonedDateTime.now(ZoneId.of("UTC"))))
        val response = TripsFareCalculationResponse(35, request.map { it.toDomainTrip() })
        doReturn(response).`when`(tripService).process(request)

        mockMvc.perform(post("/api/v1/trips")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(content().string(mapper.writeValueAsString(response)))
                .andExpect(status().isOk)

        verify(tripService, times(1)).process(request)
    }
}
