package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchedEffectExampleVM @Inject
    constructor() : ViewModel() {
        private val _sharedFlow = MutableSharedFlow<ScreenEvents>()
        val sharedFlow = _sharedFlow.asSharedFlow()

        init {
            viewModelScope.launch {
                _sharedFlow.emit(ScreenEvents.ShowSnackBar("Error!"))
            }
        }
    }

sealed class ScreenEvents {
    data class ShowSnackBar(val message: String) : ScreenEvents()

    data class Navigate(val route: String) : ScreenEvents()
}
