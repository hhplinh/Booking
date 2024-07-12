package com.example.booking.ui.booking

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booking.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TransportBookingViewModel : ViewModel() {

    private val _departureCity = MutableStateFlow("New York")
    val departureCity get() = _departureCity

    private val _arrivalCity = MutableStateFlow("London")
    val arrivalCity get() = _arrivalCity

    private val _departureCityTextFieldValue =
        MutableStateFlow(TextFieldValue(_departureCity.value))
    val departureCityTextFieldValue: StateFlow<TextFieldValue> = _departureCityTextFieldValue

    private val _arrivalCityTextFieldValue = MutableStateFlow(TextFieldValue(_arrivalCity.value))
    val arrivalCityTextFieldValue: StateFlow<TextFieldValue> = _arrivalCityTextFieldValue

    private val _seatNumber = MutableStateFlow("A2")
    val seatNumber get() = _seatNumber


    var passengers by mutableIntStateOf(1)
    var babyLuggage by mutableIntStateOf(0)
    var animalLuggage by mutableIntStateOf(0)
    var valiLuggage by mutableIntStateOf(0)


    private val _departureDate = MutableStateFlow(LocalDate.now())
    val departureDate: StateFlow<LocalDate> = _departureDate

    private val _arrivalDate = MutableStateFlow(LocalDate.now().plusDays(5))
    val arrivalDate: StateFlow<LocalDate> = _arrivalDate

    private val _departureHour = MutableStateFlow("10AM")
    val departureHour get() = _departureHour

    private val _arrivalHour = MutableStateFlow("03PM")
    val arrivalHour get() = _arrivalHour


    private val _selectedClass = MutableStateFlow("")
    val selectedClass get() = _selectedClass

    private val _selectedTransport = MutableStateFlow(R.string.flight.toString())
    val selectedTransport get() = _selectedTransport


    fun updateDepartureDate(date: LocalDate) {
        viewModelScope.launch {
            _departureDate.value = date
        }
    }

    fun updateArrivalDate(date: LocalDate) {
        viewModelScope.launch {
            _arrivalDate.value = date
        }
    }

    fun updateDepartureHour(index: Int) {
        viewModelScope.launch {
            if (index in DepartureHourChoice.indices) {
                _departureHour.value = DepartureHourChoice[index]
            }
        }
    }

    fun updateArrivalHour(index: Int) {
        viewModelScope.launch {
            if (index in DepartureHourChoice.indices) {
                _arrivalHour.value = DepartureHourChoice[index]
            }
        }
    }

    fun updateDepartureCity(city: String) {
        viewModelScope.launch {
            _departureCity.value = city
            _departureCityTextFieldValue.value = TextFieldValue(city)
        }
    }

    fun updateArrivalCity(city: String) {
        viewModelScope.launch {
            _arrivalCity.value = city
            _arrivalCityTextFieldValue.value = TextFieldValue(city)
        }
    }

    fun updateSelectedClass(option: String) {
        viewModelScope.launch {
            _selectedClass.value = option
        }
    }

    fun updateSeatNumber(seat: String) {
        viewModelScope.launch {
            _seatNumber.value = seat
        }
    }

    private val _seatNumbers = MutableStateFlow<List<String>>(emptyList())
    val seatNumbers: StateFlow<List<String>> get() = _seatNumbers


    fun updateSeatNumber(seat: String, index: Int) {
        viewModelScope.launch {
            val currentSeats = _seatNumbers.value.toMutableList()
            if (index >= currentSeats.size) {
                for (i in currentSeats.size..index) {
                    currentSeats.add("")
                }
            }
            currentSeats[index] = seat
            _seatNumbers.value = currentSeats
        }
    }

    fun addSeatNumber(seat: String) {
        viewModelScope.launch {
            val currentSeats = _seatNumbers.value.toMutableList()
            currentSeats.add(seat)
            _seatNumbers.value = currentSeats
        }
    }

    fun removeSeatNumber(index: Int) {
        viewModelScope.launch {
            val currentSeats = _seatNumbers.value.toMutableList()
            if (index < currentSeats.size) {
                currentSeats.removeAt(index)
                _seatNumbers.value = currentSeats
            }
        }
    }


//    fun updateSelectedTransport(option: String) {
//        viewModelScope.launch {
//            _selectedTransport.value = option
//        }
//    }

    fun setTransportToFlight() {
        viewModelScope.launch {
            _selectedTransport.value = R.string.flight.toString()
        }
    }

    fun swapCities() {
        viewModelScope.launch {
            val tempCity = _departureCity.value
            _departureCity.value = _arrivalCity.value
            _arrivalCity.value = tempCity

            _departureCityTextFieldValue.value = TextFieldValue(_departureCity.value)
            _arrivalCityTextFieldValue.value = TextFieldValue(_arrivalCity.value)
        }
    }

    private fun getDayGap(): Int {
        return (_arrivalDate.value.dayOfYear - _departureDate.value.dayOfYear) % 6
    }

//    fun getDates(): List<Pair<LocalDate, String>> {
//        val dayGap = getDayGap()
//        val dates = mutableListOf<Pair<LocalDate, String>>()
//        for (i in 0 until dayGap) {
//            val date = _departureDate.value.plusDays(i.toLong())
//            dates.add(date to date.dayOfWeek.toString())
//        }
//        return dates
//    }

    fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d MMM")
        return date.format(formatter)
    }

    fun formatDayOnly(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d")
        return date.format(formatter)
    }

    fun getFormattedDates(): List<Pair<String, String>> {
        val dayGap = getDayGap()
        val dates = mutableListOf<Pair<String, String>>()
        for (i in 0 until dayGap) {
            val date = _departureDate.value.plusDays(i.toLong())
            val formattedDate = formatDayOnly(date)
            val dayOfWeekShort = date.dayOfWeek.toString().take(2).uppercase()
            dates.add(formattedDate to dayOfWeekShort)
        }
        return dates
    }
}
