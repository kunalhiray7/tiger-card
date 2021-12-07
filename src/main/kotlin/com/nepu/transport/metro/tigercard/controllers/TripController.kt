package com.nepu.transport.metro.tigercard.controllers

import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import com.nepu.transport.metro.tigercard.services.TripService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TripController(private val tripService: TripService) {

    @PutMapping("/api/v1/fare-calculations/trips")
    fun processNewTrip(@RequestBody trips: List<TripFareCalculationRequest>) = tripService.process(trips)

}
