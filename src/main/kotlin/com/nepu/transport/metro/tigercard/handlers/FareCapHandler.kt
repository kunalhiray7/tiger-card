package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

interface FareCapHandler {
    fun handleTrip(currentTrip: Trip, allTrips: List<Trip>, totalDayFare: Int)
    fun setNext(handler: FareCapHandler)
}
