package com.nepu.transport.metro.tigercard.domain

import java.time.ZonedDateTime

data class Trip(
        val commuterId: Int,
        val tripDateTime: ZonedDateTime,
        val fromZone: Zone,
        val toZone: Zone,
        var baseFare: Int = 0,
        var calculatedFare: Int = 0,
        var remark: String = "Base fare applied. "
)
