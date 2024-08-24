package com.soleel.finanzas.core.common.eventmanager

class SingleEventManager {
    private var lastEventTimeMs: Long = 0
    private val eventDelayMs: Long = 300

    fun processEvent(action: () -> Unit) {
        val now = System.currentTimeMillis()
        if (now - lastEventTimeMs >= eventDelayMs) {
            action()
            lastEventTimeMs = now
        }
    }
}