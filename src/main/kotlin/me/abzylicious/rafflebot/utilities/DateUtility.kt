package me.abzylicious.rafflebot.utilities

data class Period(val days: Long, val hours: Long, val minutes: Long, val seconds: Long)

fun fullTimeToString(milliseconds: Long): String {
    val period = toPeriod(milliseconds)
    return ("${period.days} days, ${period.hours} hours, ${period.minutes} minutes, ${period.seconds} seconds")
}

fun timeToString(milliseconds: Long): String {
    val period = toPeriod(milliseconds)
    val dayString = if (period.days > 0) "${period.days} day(s) " else ""
    val hourString = if (period.hours > 0) "${period.hours} hour(s) " else ""
    val minuteString = if (period.minutes > 0) "${period.minutes} minute(s) " else ""
    val secondString = if (period.seconds > 0) "${period.seconds} second(s)" else ""
    return ("$dayString$hourString$minuteString$secondString")
}

private fun toPeriod(milliseconds: Long): Period {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / (1000 * 60)) % 60
    val hours = (milliseconds / (1000 * 60 * 60)) % 24
    val days = (milliseconds / (1000 * 60 * 60 * 24))
    return Period(days, hours, minutes, seconds)
}