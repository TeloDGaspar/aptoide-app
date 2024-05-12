package com.aptoide_app.presentation

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.lifecycle.ViewModel
import com.aptoide_app.domain.AppsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.viewModelScope
import com.aptoide_app.domain.FullDetailApp
import com.aptoide_app.domain.SimplifiedApp
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ViewModelTest @Inject constructor(
    private val apps: AppsRepository
) : ViewModel() {

    private val _simplifiedApp: MutableStateFlow<List<SimplifiedApp>> =
        MutableStateFlow(emptyList())
    val simplifiedApp: StateFlow<List<SimplifiedApp>> = _simplifiedApp.asStateFlow()

    private val _fullDetailApp: MutableStateFlow<List<FullDetailApp>> =
        MutableStateFlow(emptyList())
    val fullDetailApp: StateFlow<List<FullDetailApp>> = _fullDetailApp.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getSimplifiedApps()
        getFullDetailApp()
    }


    private fun getFullDetailApp() {/*apps.getFullDetailsApps().fold(onSuccess = {
            it.flowOn(Dispatchers.Default).onEach { appItems -> _fullDetailApp.value = appItems }
                .launchIn(viewModelScope)
        }, onFailure = { throwable ->
            _errorMessage.value = "An error occurred: ${throwable.message}"
        })*/
        _isLoading.value = true // Set loading state
        apps.getFullDetailsApps().fold(onSuccess = { response ->
            response.flowOn(Dispatchers.Default).onEach { appItems ->
                _fullDetailApp.value = appItems
            }.launchIn(viewModelScope)
            _isLoading.value = false // Reset loading state when successful
        }, onFailure = { throwable ->
            // Handle the failure here
            // For example, you can set an error state in your MutableStateFlow
            _errorMessage.value = "An error occurred: ${throwable.message}"
            _isLoading.value = false // Reset loading state when failed
        })
    }

    fun getSimplifiedApps() {
        /*apps.getSimplifiedApps().fold(onSuccess = {
            it.flowOn(Dispatchers.Default).onEach { appItems -> _simplifiedApp.value = appItems }
                .launchIn(viewModelScope)
        }, onFailure = { throwable ->
            _errorMessage.value = "An error occurred: ${throwable.message}"
        })*/
        _isLoading.value = true // Set loading state
        apps.getSimplifiedApps().fold(onSuccess = { response ->
            response.flowOn(Dispatchers.Default).onEach { appItems ->
                _simplifiedApp.value = appItems
            }.launchIn(viewModelScope)
            _isLoading.value = false // Reset loading state when successful
        }, onFailure = { throwable ->
            // Handle the failure here
            // For example, you can set an error state in your MutableStateFlow
            _errorMessage.value = "An error occurred: ${throwable.message}"
            _isLoading.value = false // Reset loading state when failed
        })
    }
}
