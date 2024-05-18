package com.example.flightapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightapp.FlightApplication
import com.example.flightapp.data.AirportData
import com.example.flightapp.data.Dao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightViewModel(
    val dao: Dao
): ViewModel() {

    private val _uiState = MutableStateFlow(FlightUiState())
    val uiState: StateFlow<FlightUiState> = _uiState.asStateFlow()

    fun updateState(text: String) {
        _uiState.update {
            it.copy(
                searchText = text
            )
        }
    }

    fun getFlightDataByHint(text: String) {
        if (text.isNotEmpty()){
            viewModelScope.launch {
                dao.getFlightByHint(text).map { airportData->
                    FlightUiState(flightList = airportData)
                }.collect { state ->
                    _uiState.update {
                        it.copy(
                            flightList = state.flightList
                        )
                    }
                }
            }
        }
    }

    fun getDataByCode(code: String) {
        viewModelScope.launch {
            dao.getFlightByCode(code = code).map {
                FlightUiState(flightByCode = it)
            }.collect { state ->
                _uiState.update {
                    it.copy(
                        flightByCode = state.flightByCode
                    )
                }
            }
        }

    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightApplication)
                FlightViewModel(
                    application.database.dao()
                )
            }
        }
    }
}

data class FlightUiState(
    val searchText: String = "",
    val flightList: List<AirportData> = listOf(),
    val flightByCode: List<AirportData> = listOf()
)
