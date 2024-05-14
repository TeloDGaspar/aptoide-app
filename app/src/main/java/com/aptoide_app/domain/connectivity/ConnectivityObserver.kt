package com.aptoide_app.domain.connectivity

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityObserver {
    val observe: StateFlow<Status>
    enum class Status{
        Available, Unavailable, Losing, Lost, Initial, CapabilitiesChanged, LinkPropertiesChanged, BlockedStatusChanged
    }
}