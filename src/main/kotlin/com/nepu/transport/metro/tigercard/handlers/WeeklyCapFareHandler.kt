package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

class WeeklyCapFareHandler : FareCapHandler {

    private lateinit var nextHandler: FareCapHandler
    private lateinit var maxCapWithNewWeekIndex: Pair<Int, Int>
    private val weekStartDay = "MONDAY"
    private var weekFare: Int = 0
    private var currentDay: String = ""
    private var firstDayProcessed = false
    private var firstDayOfTrip = ""

    override fun handleTrip(currentTrip: Trip, allTrips: List<Trip>, totalDayFare: Int) {

        currentDay = currentTrip.tripDateTime.dayOfWeek.name
        if (!this::maxCapWithNewWeekIndex.isInitialized) {
            maxCapWithNewWeekIndex = getMaxCapForTrips(allTrips, 0)
            firstDayOfTrip = allTrips[0].tripDateTime.dayOfWeek.name
        }

        if (currentDay == weekStartDay && firstDayProcessed) {
            // new week
            // get max cap for the new week trips
            weekFare = 0
            maxCapWithNewWeekIndex = getMaxCapForTrips(allTrips, maxCapWithNewWeekIndex.second)
        }

        if (weekFare + currentTrip.baseFare >= maxCapWithNewWeekIndex.first) {
            currentTrip.calculatedFare = maxCapWithNewWeekIndex.first - weekFare
            currentTrip.remark += "Weekly cap reached. "
            weekFare = maxCapWithNewWeekIndex.first
        } else {
            weekFare += totalDayFare
        }
        if (currentDay != firstDayOfTrip) {
            firstDayProcessed = true
        }

        if (this::nextHandler.isInitialized) {
            nextHandler.handleTrip(currentTrip, allTrips, totalDayFare)
        }
    }

    override fun setNext(handler: FareCapHandler) {
        nextHandler = handler
    }

    private fun getMaxCapForTrips(trips: List<Trip>, newWeekIndex: Int): Pair<Int, Int> {
        var maxCap = 0
        val subTrips = trips.subList(newWeekIndex, trips.size)
        var day = subTrips[0].tripDateTime.dayOfWeek.name
        val firstDayOfTrip = subTrips[0].tripDateTime.dayOfWeek.name
        var firstDayProcessed = false

        subTrips.forEachIndexed { index, trip ->
            if (day != weekStartDay || !firstDayProcessed) {
                val weeklyCapSingleTrip = trip.fromZone.getWeeklyCapTo(trip.toZone)
                if (weeklyCapSingleTrip > maxCap) {
                    maxCap = weeklyCapSingleTrip
                }
            } else {
                return Pair(maxCap, index)
            }
            day = trip.tripDateTime.dayOfWeek.name
            if (day != firstDayOfTrip) {
                firstDayProcessed = true
            }
        }

        return Pair(maxCap, trips.lastIndex)
    }
}
