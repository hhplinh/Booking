package com.example.booking.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.booking.R

data class BookingOption(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)

class Datasource() {
    fun loadBookingOptions(): List<BookingOption> {
        return listOf<BookingOption>(
            BookingOption(R.string.b1,R.drawable.b1),
            BookingOption(R.string.b2,R.drawable.b2),
            BookingOption(R.string.b3,R.drawable.b3),
            BookingOption(R.string.b4,R.drawable.b4)
        )
}
}