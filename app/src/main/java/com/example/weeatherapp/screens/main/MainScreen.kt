package com.example.weeatherapp.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weeatherapp.data.DataOrException
import com.example.weeatherapp.model.Weather
import com.example.weeatherapp.widgets.HumidityWindPressureRow
import com.example.weeatherapp.widgets.Next5DaysRow
import com.example.weeatherapp.widgets.SunsetSunriseRow
import com.example.weeatherapp.widgets.WeatherAppBar
import com.example.weeatherapp.widgets.WeatherStateImage
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewModel.getWeatherData("Porto Alegre")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            navController = navController
        )

    }) { padding ->
        MainContent(data = weather, padding = padding)
    }

}

@Composable
fun MainContent(data: Weather, padding: PaddingValues) {
    val baseIconUrl = "https://openweathermap.org/img/wn"

    Column(
        Modifier
            .padding(padding)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = DateTimeFormatter.ofPattern("EEE, MMM dd", Locale.ENGLISH)
                .withZone(ZoneId.systemDefault())
                .format(Instant.ofEpochSecond(data.list[0].dt.toLong())),
            style = MaterialTheme.typography.bodyMedium
        )

        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(
                    baseIconUrl = baseIconUrl,
                    iconReference = data.list[0].weather[0].icon
                )
                Text(
                    text = "${"%.1f".format(data.list[0].main.temp)}º",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = data.list[0].weather[0].main)
            }

        }
        HumidityWindPressureRow(weather = data)
        HorizontalDivider()
        SunsetSunriseRow(weather = data)
        Next5DaysRow(weather = data, baseIconUrl = baseIconUrl)
    }
}
