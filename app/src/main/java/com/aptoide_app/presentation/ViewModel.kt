package com.aptoide_app.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aptoide_app.domain.AppsRepository
import com.aptoide_app.domain.ConnectivityObserver
import com.aptoide_app.domain.FullDetailApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelTest @Inject constructor(
    private val apps: AppsRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val _fullDetailApp: MutableStateFlow<List<FullDetailApp>> =
        MutableStateFlow(emptyList())
    val fullDetailApp: StateFlow<List<FullDetailApp>> = _fullDetailApp.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _network = MutableStateFlow(
        ConnectivityObserver.Status.Initial
    )
    val network: StateFlow<ConnectivityObserver.Status> = _network.asStateFlow()

    init {
        getFullDetailApp()
        handleNetwork()
    }

    private fun handleNetwork() {
        connectivityObserver.observe.onEach {
            _network.value = it
        }.launchIn(viewModelScope)
    }

    fun getFullDetailApp() {
        _isLoading.value = true
        apps.getFullDetailsApps().fold(onSuccess = { response ->
            response.onEach { appItems ->
                _fullDetailApp.value = appItems
                Log.i("response", "fullDetailApp ${fullDetailApp}")
            }.launchIn(viewModelScope)
        }, onFailure = { throwable ->
            _isLoading.value = false
        })
    }
}
