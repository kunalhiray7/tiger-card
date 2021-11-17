package com.nepu.transport.metro.tigercard.controllers

import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import com.nepu.transport.metro.tigercard.services.TripService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TripController(private val tripService: TripService) {

    @PostMapping("/api/v1/trips")
    fun processNewTrip(@RequestBody trips: List<TripFareCalculationRequest>) = tripService.process(trips)

}
