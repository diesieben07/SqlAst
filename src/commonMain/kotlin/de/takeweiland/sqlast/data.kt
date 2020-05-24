package de.takeweiland.sqlast

expect class BigDecimal

expect class Instant
expect fun instantOf(unixTime: Long): Instant

expect class LocalDate
expect fun localDateOf(year: Int, month: Int, day: Int): LocalDate

expect class LocalTime
expect fun localTimeOf(hour: Int, minute: Int, second: Int, nano: Int): LocalTime

expect class PeriodDuration
expect fun periodDurationOf(years: Int, months: Int, days: Int, seconds: Long, nano: Int): PeriodDuration