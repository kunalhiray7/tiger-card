package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

class DailyCapFareHandler : FareCapHandler {
    private lateinit var nextHandler: FareCapHandler

    override fun handle(trips: List<Trip>) {
        val sortedTrips = trips.sortedBy { it.tripDateTime }
        var dayFare = 0
        val maxCap = getMaxCapForTrips(sortedTrips)

        sortedTrips.forEach {
            it.baseFare = it.fromZone.getBaseFareTo(it.toZone, it.tripDateTime)
            it.calculatedFare = it.baseFare

            if (dayFare + it.calculatedFare >= maxCap) {
                it.calculatedFare = maxCap - dayFare
                dayFare = maxCap
            } else {
                dayFare += it.calculatedFare
            }
        }

        if (::nextHandler.isInitialized) {
            nextHandler.handle(trips)
        }
    }

    override fun setNext(handler: FareCapHandler) {
        nextHandler = handler
    }

    private fun getMaxCapForTrips(trips: List<Trip>): Int {
        var maxCap = 0
        trips.forEach {
            val dailyCapSingleTrip = it.fromZone.getDailyCapTo(it.toZone)
            if (dailyCapSingleTrip > maxCap) {
                maxCap = dailyCapSingleTrip
            }
        }
        return maxCap
    }
}
