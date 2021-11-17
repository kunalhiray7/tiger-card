package com.nepu.transport.metro.tigercard.services

import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import com.nepu.transport.metro.tigercard.dtos.TripsFareCalculationResponse
import com.nepu.transport.metro.tigercard.handlers.DailyCapFareHandler
import com.nepu.transport.metro.tigercard.handlers.FareCapHandler
import com.nepu.transport.metro.tigercard.handlers.WeeklyCapFareHandler
import org.springframework.stereotype.Service

@Service
class TripService {

    fun process(tripRequests: List<TripFareCalculationRequest>): TripsFareCalculationResponse {
        val handlerChain = createHandlerChain()
        val trips = tripRequests.map { it.toDomainTrip() }.sortedBy { it.tripDateTime }

        trips.forEach {
            handlerChain.handleTrip(it, trips)
        }

        return TripsFareCalculationResponse(
                totalFareForAllTrips = trips.sumOf { it.calculatedFare },
                trips = trips
        )
    }

    private fun createHandlerChain(): FareCapHandler {
        val chain = DailyCapFareHandler()
        chain.setNext(WeeklyCapFareHandler())
        return chain
    }
}
