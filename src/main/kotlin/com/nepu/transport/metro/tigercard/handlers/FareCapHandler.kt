package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

interface FareCapHandler {
    fun handleTrip(currentTrip: Trip, allTrips: List<Trip>)

    fun setNext(handler: FareCapHandler)

    fun updateFare(consolidatedFare: Int, currentTrip: Trip, maxCapWithIndex: Pair<Int, Int>, capType: String): Int =
            if (consolidatedFare + currentTrip.baseFare >= maxCapWithIndex.first) {
                currentTrip.calculatedFare = maxCapWithIndex.first - consolidatedFare
                currentTrip.remark += "$capType cap reached. "
                maxCapWithIndex.first
            } else {
                consolidatedFare + currentTrip.calculatedFare
            }
}
