package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

class WeeklyCapFareHandler: FareCapHandler {

    private lateinit var nextHandler: FareCapHandler

    override fun handleTrip(currentTrip: Trip, allTrips: List<Trip>) {

        if(this::nextHandler.isInitialized) {
            nextHandler.handleTrip(currentTrip, allTrips)
        }
    }

    override fun setNext(handler: FareCapHandler) {
        nextHandler = handler
    }
}
