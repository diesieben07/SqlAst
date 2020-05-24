package de.takeweiland.sqlast

import java.time.Duration
import java.time.Period

actual typealias BigDecimal = java.math.BigDecimal

actual typealias Instant = java.time.Instant
actual fun instantOf(unixTime: Long) = Instant.ofEpochMilli(unixTime)

actual typealias LocalDate = java.time.LocalDate
actual fun localDateOf(year: Int, month: Int, day: Int)= LocalDate.of(year, month, day)
actual val LocalDate.year: Int get() = year
actual val LocalDate.month: Int get() = monthValue
actual val LocalDate.day: Int get() = dayOfMonth

actual typealias LocalTime = java.time.LocalTime
actual fun localTimeOf(hour: Int, minute: Int, second: Int, nano: Int) = LocalTime.of(hour, minute, second, nano)
actual val LocalTime.hour: Int get() = hour
actual val LocalTime.minute: Int get() = minute
actual val LocalTime.second: Int get() = second
actual val LocalTime.nano: Int get() = nano

actual typealias PeriodDuration = org.threeten.extra.PeriodDuration
actual fun periodDurationOf(years: Int, months: Int, days: Int, seconds: Long, nano: Int) = PeriodDuration.of(
    Period.of(years, months, days),
    Duration.ofSeconds(seconds, nano.toLong())
)