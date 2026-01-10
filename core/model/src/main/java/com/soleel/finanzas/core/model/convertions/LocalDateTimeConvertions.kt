package com.soleel.finanzas.core.model.convertions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


/** Convierte un LocalDateTime que ya está en UTC a la zona del dispositivo. */
fun LocalDateTime.utcToDevice(): LocalDateTime {
    val instant: Instant = this.atZone(ZoneOffset.UTC).toInstant()
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
}

/** Convierte un LocalDateTime que viene del cliente (zona local) a UTC. */
fun LocalDateTime.deviceToUtc(): LocalDateTime {
    val instant: Instant = this.atZone(ZoneId.systemDefault()).toInstant()
    return instant.atZone(ZoneOffset.UTC).toLocalDateTime()
}