package de.takeweiland.sqlast

import de.takeweiland.sqlast.util.toHexString

sealed class Expr {

    data class Identifier(val ident: String) : Expr()
    object Wildcard : Expr()
    data class QualifiedWildcard(val ident: List<String>) : Expr()
    data class CompoundIdentifier(val ident: List<String>) : Expr()
    data class IsNull(val expr: Expr) : Expr()
    data class IsNotNull(val expr: Expr) : Expr()
    data class InList(val expr: Expr, val list: List<Expr>, val negated: Boolean) : Expr()
    data class InSubquery(val expr: Expr, val subquery: Nothing, val negated: Boolean) : Expr()
    data class Between(val expr: Expr, val negated: Boolean, val low: Expr, val high: Expr) : Expr()
    data class BinaryOp(val left: Expr, val op: BinaryOperator, val right: Expr) : Expr()
    data class UnaryOp(val op: UnaryOperator, val expr: Expr) : Expr()
    data class Cast(val expr: Expr, val dataType: DataType): Expr()
    data class Extract(val expr: Expr, val field: DateTimeField) : Expr()
    data class Collate(val expr: Expr, val collation: String) : Expr()
    data class Nested(val expr: Expr) : Expr()

    data class Function(
        val name: ObjectName,
        val args: List<Expr>,
        // TODO: OVER, distinct
    ) : Expr()
    data class Case(val subject: Expr?, val branches: List<Branch>, val elseExpr: Expr?) : Expr() {
        data class Branch(val condition: Expr, val result: Expr)
    }
    data class Exists(val query: Query): Expr()
    data class Subquery(val query: Query): Expr()

}

enum class DateTimeField {
    YEAR,
    MONTH,
    DAY,
    HOUR,
    MINUTE,
    SECOND
}

sealed class Value : Expr() {

    data class Boolean(val value: kotlin.Boolean) : Value()
    data class Int(val value: kotlin.Int) : Value()
    data class Long(val value: kotlin.Long) : Value()
    data class Double(val value: kotlin.Double) : Value()
    data class Number(val value: BigDecimal) : Value()

    data class String(val value: kotlin.String) : Value()
    data class NString(val value: kotlin.String) : Value()
    data class HexString(val value: kotlin.String) : Value() {
        constructor(value: ByteArray) : this(value.toHexString())
    }

    data class Date(val value: LocalDate) : Value()
    data class Time(val value: LocalTime) : Value()
    data class Timestamp(val value: Interval) : Value()
    data class Interval(val value: PeriodDuration) : Value()
    object Null : Value()

}

sealed class DataType {
    data class Char(val length: Long?) : DataType()
    data class Varchar(val length: Long) : DataType()
    object UUID : DataType()
    data class Clob(val length: Long) : DataType()
    data class Binary(val length: Long) : DataType()
    data class Varbinary(val length: Long) : DataType()
    data class Blob(val length: Long) : DataType()
    data class Decimal(val precision: Long?, val scale: Long?) : DataType()
    data class Float(val precision: Long?) : DataType()
    object SmallInt : DataType()
    object Int : DataType()
    object BigInt : DataType()
    object Real : DataType()
    object Double : DataType()
    object Boolean : DataType()
    object Date : DataType()
    object Time : DataType()
    object Timestamp : DataType()
    object Interval : DataType()
    object Text : DataType()
    data class Custom(val identifier: String) : DataType()
    data class Array(val elementType: DataType) : DataType()
}