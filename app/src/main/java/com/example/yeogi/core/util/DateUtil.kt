package com.example.yeogi.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

val monthDayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM.dd")
val dayOfWeekFormatter: (LocalDate) -> String = { date -> date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN) }

fun LocalDate.getFormattedMonthDay(): String {
    return "${this.format(monthDayFormatter)}(${dayOfWeekFormatter(this)})"
}