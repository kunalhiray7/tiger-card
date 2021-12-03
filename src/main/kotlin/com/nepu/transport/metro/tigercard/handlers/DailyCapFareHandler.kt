package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip
import org.slf4j.LoggerFactory
import java.time.LocalDate

class DailyCapFareHandler : FareCapHandler {

    private val logger = LoggerFactory.getLogger(DailyCapFareHandler::class.java)
    private var nextHandler: FareCapHandler? = null
    private lateinit var maxCapWithNewDayIndex: Pair<Int, Int>
    private var dayFare: Int = 0
    private lateinit var currentDay: LocalDate

    override fun handleTrip(currentTrip: Trip, allTrips: List<Trip>) {
        currentTrip.baseFare = currentTrip.fromZone.getBaseFareTo(currentTrip.toZone, currentTrip.tripDateTime)
        currentTrip.calculatedFare = currentTrip.baseFare

        if(!this::maxCapWithNewDayIndex.isInitialized && !this::currentDay.isInitialized) {
            initializeMaxCap(allTrips, currentTrip)
        }

        if (currentDay != currentTrip.tripDateTime.toLocalDate()) {
            startNewDay(currentTrip, allTrips)
        }

        dayFare = updateFare(dayFare, currentTrip, maxCapWithNewDayIndex, "Daily")

        handleNext(currentTrip, allTrips)
    }

    override fun setNext(handler: FareCapHandler) {
        nextHandler = handler
    }

    override fun getNext() = nextHandler

    private fun initializeMaxCap(allTrips: List<Trip>, currentTrip: Trip) {
        maxCapWithNewDayIndex = getMaxCapForDay(allTrips, 0)
        currentDay = currentTrip.tripDateTime.toLocalDate()
        logger.debug("Initial daily max cap:: $maxCapWithNewDayIndex")
    }

    private fun startNewDay(currentTrip: Trip, allTrips: List<Trip>) {
        dayFare = 0
        currentDay = currentTrip.tripDateTime.toLocalDate()
        maxCapWithNewDayIndex = getMaxCapForDay(allTrips, maxCapWithNewDayIndex.second)
        logger.debug("New day started, new daily max cap:: $maxCapWithNewDayIndex")
    }

    /**
     * Determines the maximum cap applicable for the trips in a day.
     * Returns the Pair<Int, Int> where first element is maximum applicable cap
     * and second element is the index of the trip at which new day starts.
     * The index is required to compute max applicable cap for a particular day and not for
     * all the trips in the list.
     */
    private fun getMaxCapForDay(trips: List<Trip>, newDayIndex: Int): Pair<Int, Int> {
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
