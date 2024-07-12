package com.example.booking.ui.booking

object CityOptions {
    val departureOptions = listOf("New York", "London")
    val arrivalOptions = departureOptions.reversed()
}

data class TicketInfo(
    val departureCity: String,
    val arrivalCity: String,
    val departureDate: String,
    val departureHour: String,
    val seatNumber: String
)

val transportationMethods = listOf("Flight")

val departureTime = listOf("12AM - 06AM", "06AM - 12PM", "12PM - 06PM", "06PM - 12AM")
val arrivalTime = departureTime
val DepartureHourChoice = listOf("04AM", "10AM", "03PM", "10PM")
val sortByOptions = listOf("Arrival time", "Departure Time", "Price", "Lowest fare", "Duration")