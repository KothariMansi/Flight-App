package com.example.flightapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightapp.ui.theme.FlightAppTheme

enum class Screen{
    Suggestion,
    Selected
}

@Composable
fun FlightScreen(
    modifier: Modifier = Modifier,
    flightViewModel: FlightViewModel = viewModel(factory = FlightViewModel.factory)
) {
    val flightUiState by flightViewModel.uiState.collectAsState()

    var screen: Screen = Screen.Suggestion

    Scaffold(
        topBar = {
            FlightTopBar()
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = flightUiState.searchText,
                onValueChange = { text ->
                    flightViewModel.updateState(text)
                    flightViewModel.getFlightDataByHint(text)
                    screen = Screen.Suggestion
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = null
                    )
                },
            )

            if (screen == Screen.Suggestion){
                LazyColumn {
                    if (flightUiState.flightList.isNotEmpty()){
                        items(flightUiState.flightList) {airportData ->
                            FlightSearchListItem(
                                code = airportData.iataCode,
                                name = airportData.name,
                                changeScreen = { screen = Screen.Selected },
                                getDataByCode = {flightViewModel.getDataByCode(code = airportData.iataCode)},
                                modifier = modifier
                            )
                        }
                    }
                }
            } else {
                LazyColumn {
                    if (flightUiState.flightByCode.isNotEmpty()) {
                        items(flightUiState.flightByCode){airportData ->
                            FlightListItem(
                                code = airportData.iataCode,
                                name = airportData.name,
                                passengers = airportData.passengers.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FlightSearchListItem(
    code: String,
    name: String,
    changeScreen:() -> Unit,
    getDataByCode: () -> Unit,
    modifier: Modifier = Modifier

) {
    TextButton(
        onClick = {
            changeScreen()
            getDataByCode()
                  }, modifier = modifier
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
            Text(
                text = code,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0, 0, 0)
            )
            Spacer(modifier = modifier.padding(4.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0, 0, 0)
            )
        }
    }
}

@Preview
@Composable
fun FlightSearchListItemPreview() {
    FlightAppTheme {
        Column {
            FlightSearchListItem(
                code = "ASA",
                name = " Airport is Airport",
                changeScreen = {  },
                getDataByCode = {  }
            )
            FlightSearchListItem(
                code = "ASA",
                name = " Airport is Airport",
                changeScreen = {  },
                getDataByCode = {  }
            )
        }
    }
}

@Composable
fun FlightListItem(
    code: String,
    name: String,
    passengers: String
) {
    Column {
        Text(text = code)
        Text(text = name)
        Text(text = passengers)
        Text(text = "")
    }
}
