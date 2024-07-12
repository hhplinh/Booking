import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.booking.ui.notification.RetrofitInstance
import com.example.booking.ui.notification.WeatherResponse
import kotlinx.coroutines.launch

//@Composable
//fun WeatherInfo(location: String) {
//    val weatherService = RetrofitInstance.weatherService
//    val scope = rememberCoroutineScope()
//    var weather by remember { mutableStateOf<WeatherApiResponse?>(null) }
//    var isLoading by remember { mutableStateOf(true) }
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//
//    LaunchedEffect(location) {
//        scope.launch {
//            try {
//                weather = weatherService.getCurrentWeather(apiKey = "e3b0256b41ef41e0b88163813241007", location = location)
//                isLoading = false
//            } catch (e: Exception) {
//                errorMessage = e.localizedMessage ?: "Failed to load weather data"
//                isLoading = false
//            }
//        }
//    }
//

//@Composable
//fun saveWeatherData(context: Context, weatherResponse: WeatherResponse) {
//    val sharedPreferences = context.getSharedPreferences("WeatherData", Context.MODE_PRIVATE)
//    val editor = sharedPreferences.edit()
//    val gson = Gson()
//    val json = gson.toJson(weatherResponse)
//    editor.putString("lastWeatherData", json)
//    editor.apply()
//}
//
//@Composable
//fun getSavedWeatherData(context: Context): WeatherResponse? {
//    val sharedPreferences = context.getSharedPreferences("WeatherData", Context.MODE_PRIVATE)
//    val gson = Gson()
//    val json = sharedPreferences.getString("lastWeatherData", null)
//    return if (json != null) {
//        gson.fromJson(json, WeatherResponse::class.java)
//    } else {
//        null
//    }
//}
@Composable
fun CheckPermission() {
    val context = LocalContext.current
    val hasPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            hasPermission.value = isGranted
        }
    )

    if (!hasPermission.value) {
        Button(onClick = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
            Text("Request Permission")
        }
    } else {
        Text("Permission Granted")
    }
}


@Composable
fun WeatherInfo(location: String) {
    val weatherService = RetrofitInstance.weatherService
    val scope = rememberCoroutineScope()
    var weather by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(location) { // Use location as a key for LaunchedEffect to re-fetch data when location changes
        scope.launch {
            try {
                weather = weatherService.getTodayWeather(location = location)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Failed to load weather data"
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Text("Loading...")
    } else if (errorMessage != null) {
        Text(errorMessage!!)
    } else {
        weather?.let {
            Column {
                Text("Location: ${it.location}")
                Text("Temperature: ${it.temperature}°C")
                Text("Description: ${it.description}")
                Text("Humidity: ${it.humidity}%")
                Text("Pressure: ${it.pressure} hPa")
                Text("Wind Speed: ${it.windSpeed} m/s")
            }
        }
    }
}

@Composable
fun NotiScreen() {
    Column {
        CheckPermission()
        WeatherInfo(location = "London")
        WeatherInfo(location = "New York")
        WeatherInfo(location = "Paris")
        WeatherInfo(location = "Berlin")
    }
}