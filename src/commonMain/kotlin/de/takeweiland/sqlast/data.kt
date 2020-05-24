package de.takeweiland.sqlast

expect class BigDecimal

expect class Instant
expect fun instantOf(unixTime: Long): Instant
//expect val Instant.unixTime: Long

expect class LocalDate
expect fun localDateOf(year: Int, month: Int, day: Int): LocalDate
expect val LocalDate.year: Int
expect val LocalDate.month: Int
expect val LocalDate.day: Int

expect class LocalTime
expect fun localTimeOf(hour: Int, minute: Int, second: Int, nano: Int): LocalTime
expect val LocalTime.hour: Int
expect val LocalTime.minute: Int
expect val LocalTime.second: Int
expect val LocalTime.nano: Int

expect class PeriodDuration
expect fun periodDurationOf(years: Int, months: Int, days: Int, seconds: Long, nano: Int): PeriodDuration