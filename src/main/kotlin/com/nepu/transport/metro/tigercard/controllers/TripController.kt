package com.nepu.transport.metro.tigercard.controllers

import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TripController {

    @PostMapping("/api/v1/trips")
    fun processNewTrip(@RequestBody request: TripFareCalculationRequest) {}

}
