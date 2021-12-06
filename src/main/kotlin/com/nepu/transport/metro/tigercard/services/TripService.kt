package com.nepu.transport.metro.tigercard.services

import com.nepu.transport.metro.tigercard.dtos.Mapper
import com.nepu.transport.metro.tigercard.dtos.TripFareCalculationRequest
import com.nepu.transport.metro.tigercard.dtos.ConsolidatedTripsFareCalculationResponse
import com.nepu.transport.metro.tigercard.handlers.DailyCapFareHandler
import com.nepu.transport.metro.tigercard.handlers.FareCapHandler
import com.nepu.transport.metro.tigercard.handlers.WeeklyCapFareHandler
import org.springframework.stereotype.Service

@Service
class TripService(private val mapper: Mapper) {

    fun process(tripRequests: List<TripFareCalculationRequest>): ConsolidatedTripsFareCalculationResponse {
        val handlerChain = createHandlerChain()
        val trips = tripRequests.map { mapper.toDomainTrip(it) }.sortedBy { it.tripDateTime }

        trips.forEach {
            handlerChain.handleTrip(it, trips)
        }

        return ConsolidatedTripsFareCalculationResponse(
                totalFareForAllTrips = trips.sumOf { it.calculatedFare },
                trips = trips.map { mapper.toTripFareCalculationResponse(it) }
        )
    }

    private fun createHandlerChain(): FareCapHandler {
        val chain = DailyCapFareHandler()
        chain.setNext(WeeklyCapFareHandler())
        return chain
    }
}
