package com.syntext.error.gitissue.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun formatTimestamp(timestamp: String?): String {
    if (timestamp.isNullOrEmpty()) return ""

    // Parse the input timestamp
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date = inputFormat.parse(timestamp)

    // Get the current date and time
    val currentTime = Date()
    val diffInMillis = currentTime.time - date.time

    // Define human-readable date formats
    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val dateFormat = SimpleDateFormat("M/dd/yyyy", Locale.getDefault())

    // Calculate difference in days
    val daysDiff = TimeUnit.MILLISECONDS.toDays(diffInMillis).toInt()

    // Determine how to display the timestamp
    return when {
        daysDiff == 0 -> "Today"
        daysDiff == 1 -> "Yesterday"
        daysDiff < 7 -> dayFormat.format(date)
        else -> dateFormat.format(date)
    }
}