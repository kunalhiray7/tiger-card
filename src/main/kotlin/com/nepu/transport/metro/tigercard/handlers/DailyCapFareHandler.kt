package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

class DailyCapFareHandler : FareCapHandler {
    private lateinit var nextHandler: FareCapHandler

    override fun handle(trips: List<Trip>) {
        val sortedTrips = trips.sortedBy { it.tripDateTime }
        var dayFare = 0
        var maxCapWithNewDayIndex = getMaxCapForTrips(sortedTrips, 0)
        var date = sortedTrips[0].tripDateTime.toLocalDate()

        sortedTrips.forEach {
            it.baseFare = it.fromZone.getBaseFareTo(it.toZone, it.tripDateTime)
            it.calculatedFare = it.baseFare

            if (date != it.tripDateTime.toLocalDate()) {
                // new day
                // get max cap for the same day trips
                dayFare = 0
                date = it.tripDateTime.toLocalDate()
                maxCapWithNewDayIndex = getMaxCapForTrips(sortedTrips, maxCapWithNewDayIndex.second)
            }

            if (dayFare + it.calculatedFare >= maxCapWithNewDayIndex.first) {
                it.calculatedFare = maxCapWithNewDayIndex.first - dayFare
                dayFare = maxCapWithNewDayIndex.first
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

    private fun getMaxCapForTrips(trips: List<Trip>, newDayIndex: Int): Pair<Int, Int> {
        var maxCap = 0
        val subTrips = trips.subList(newDayIndex, trips.size)
        val day = subTrips[0].tripDateTime.toLocalDate()

        subTrips.forEachIndexed { index, trip ->
            if (day == trip.tripDateTime.toLocalDate()) {
                val dailyCapSingleTrip = trip.fromZone.getDailyCapTo(trip.toZone)
                if (dailyCapSingleTrip > maxCap) {
                    maxCap = dailyCapSingleTrip
                }
            } else {
                return Pair(maxCap, index)
            }
        }

        return Pair(maxCap, trips.lastIndex)
    }
}
