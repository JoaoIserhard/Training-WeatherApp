package com.example.weeatherapp.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weeatherapp.R
import com.example.weeatherapp.model.Weather
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@Composable
fun Next5DaysRow(weather: Weather, baseIconUrl: String) {
    val cityOffset = ZoneOffset.ofTotalSeconds(weather.city.timezone)
    val cityToday = Instant.now().atOffset(cityOffset).toLocalDate()
    val tempFormatter = java.text.DecimalFormat("0.##")

    val dailyList = weather.list.groupBy {
        Instant.ofEpochSecond(it.dt.toLong())
            .atOffset(cityOffset)
            .toLocalDate()
    }.filterKeys { it.isAfter(cityToday) }
        .values
        .take(5)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Next 5 Days",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        dailyList.forEach { dayIntervals ->
            val maxTemp = dayIntervals.maxOf { it.main.temp_max }
            val minTemp = dayIntervals.minOf { it.main.temp_min }
            val midItem = dayIntervals[dayIntervals.size / 2]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DateTimeFormatter.ofPattern("EEE")
                        .withZone(cityOffset)
                        .format(Instant.ofEpochSecond(midItem.dt.toLong())),
                    modifier = Modifier.width(64.dp)
                )

                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WeatherStateImage(baseIconUrl, midItem.weather[0].icon)
                    Text(
                        text = midItem.weather[0].description.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Applying the formatter here
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${tempFormatter.format(maxTemp)}º",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${tempFormatter.format(minTemp)}º",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise_icon),
                contentDescription = "Sunrise Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochSecond(weather.city.sunrise.toLong()))
            )
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunset_icon),
                contentDescription = "Sunset Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochSecond(weather.city.sunrise.toLong()))
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: Weather) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity_icon),
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.list[0].main.humidity}%")
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure_icon),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.list[0].main.pressure} hPa")
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind_icon),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${weather.list[0].wind.speed} m/s")
        }
    }
}

@Composable
fun WeatherStateImage(baseIconUrl: String, iconReference: String = "01d") {
    AsyncImage(
        model = "${baseIconUrl}/${iconReference}.png",
        contentDescription = "Current weather condition",
        modifier = Modifier.size(64.dp),
        contentScale = ContentScale.Fit,
        colorFilter =
            ColorFilter.tint(Color(Color.LightGray.value), blendMode = BlendMode.Modulate)
    )
}
