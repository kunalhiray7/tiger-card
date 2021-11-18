package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.constants.AppConstants.Companion.WEEK_START_DAY
import com.nepu.transport.metro.tigercard.domain.Trip
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class WeeklyCapFareHandler : FareCapHandler {

    private val logger: Logger = LoggerFactory.getLogger(WeeklyCapFareHandler::class.java)
    private lateinit var nextHandler: FareCapHandler
    private lateinit var maxCapWithNewWeekIndex: Pair<Int, Int>
    private val weekStartDay = WEEK_START_DAY
    private var weekFare: Int = 0
    private var currentDay: String = ""
    private var firstDayProcessed = false
    private var firstDayOfTrip = ""

    override fun handleTrip(currentTrip: Trip, allTrips: List<Trip>) {

        currentDay = currentTrip.tripDateTime.dayOfWeek.name
        if (!this::maxCapWithNewWeekIndex.isInitialized) {
            maxCapWithNewWeekIndex = getMaxCapForTrips(allTrips, 0)
            firstDayOfTrip = allTrips[0].tripDateTime.dayOfWeek.name
            logger.debug("Initial max weekly cap:: $maxCapWithNewWeekIndex")
        }

        if (currentDay == weekStartDay && firstDayProcessed) {
            // new week
            // get max cap for the new week trips
            weekFare = 0
            maxCapWithNewWeekIndex = getMaxCapForTrips(allTrips, maxCapWithNewWeekIndex.second)
            logger.debug("New week started, new max weekly cap:: $maxCapWithNewWeekIndex")
        }

        if (weekFare + currentTrip.baseFare >= maxCapWithNewWeekIndex.first) {
            currentTrip.calculatedFare = maxCapWithNewWeekIndex.first - weekFare
            currentTrip.remark += "Weekly cap reached. "
            weekFare = maxCapWithNewWeekIndex.first
        } else {
            weekFare += currentTrip.calculatedFare
        }
        if (currentDay != firstDayOfTrip) {
            firstDayProcessed = true
        }

        if (this::nextHandler.isInitialized) {
            nextHandler.handleTrip(currentTrip, allTrips)
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
