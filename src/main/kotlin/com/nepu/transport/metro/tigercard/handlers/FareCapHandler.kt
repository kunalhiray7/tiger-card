package com.nepu.transport.metro.tigercard.handlers

import com.nepu.transport.metro.tigercard.domain.Trip

interface FareCapHandler {
    fun handle(trips: List<Trip>)
    fun setNext(handler: FareCapHandler)
}
