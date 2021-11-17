package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip
import java.time.LocalDate

class DailyCapFareHandler : FareCapHandler {
    private lateinit var nextHandler: FareCapHandler
    private lateinit var maxCapWithNewDayIndex: Pair<Int, Int>
    private var dayFare: Int = 0
    private lateinit var currentDay: LocalDate

    override fun handleTrip(currentTrip: Trip, allTrips: List<Trip>, totalDayFare: Int) {
        currentTrip.baseFare = currentTrip.fromZone.getBaseFareTo(currentTrip.toZone, currentTrip.tripDateTime)
        currentTrip.calculatedFare = currentTrip.baseFare

        if(!this::maxCapWithNewDayIndex.isInitialized && !this::currentDay.isInitialized) {
            maxCapWithNewDayIndex = getMaxCapForTrips(allTrips, 0)
            currentDay = currentTrip.tripDateTime.toLocalDate()
        }

        if (currentDay != currentTrip.tripDateTime.toLocalDate()) {
            // new day
            // get max cap for the same day trips
            dayFare = 0
            currentDay = currentTrip.tripDateTime.toLocalDate()
            maxCapWithNewDayIndex = getMaxCapForTrips(allTrips, maxCapWithNewDayIndex.second)
        }

        if (dayFare + currentTrip.calculatedFare >= maxCapWithNewDayIndex.first) {
            currentTrip.calculatedFare = maxCapWithNewDayIndex.first - dayFare
            currentTrip.remark += "Daily cap reached. "
            dayFare = maxCapWithNewDayIndex.first
        } else {
            dayFare += currentTrip.calculatedFare
        }

        if(this::nextHandler.isInitialized) {
            nextHandler.handleTrip(currentTrip, allTrips, dayFare)
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
