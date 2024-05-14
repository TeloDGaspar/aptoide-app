package com.aptoide_app.domain.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Fetches full details of apps.
 *
 * @return A Result object containing a Flow of a list of FullDetailApp objects.
 */
class NetworkConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {
    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Initial)
    override val observe = _networkStatus.asStateFlow()

    init {
        checkConnectivity()
    }

    /**
     * checkConnectivity is a method that checks the network connectivity status.
     * It registers a network callback that updates the _networkStatus value when the network status changes.
     */
    private fun checkConnectivity() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkStatus.value = ConnectivityObserver.Status.Available
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkStatus.value = ConnectivityObserver.Status.Lost
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                super.onLosing(network, maxMsToLive)
                _networkStatus.value = ConnectivityObserver.Status.Losing
            }

            override fun onUnavailable() {
                super.onUnavailable()
                _networkStatus.value = ConnectivityObserver.Status.Unavailable
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                _networkStatus.value = ConnectivityObserver.Status.CapabilitiesChanged
            }

            override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
                super.onLinkPropertiesChanged(network, linkProperties)
                _networkStatus.value = ConnectivityObserver.Status.LinkPropertiesChanged
            }

            override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                super.onBlockedStatusChanged(network, blocked)
                _networkStatus.value = ConnectivityObserver.Status.BlockedStatusChanged
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }
}