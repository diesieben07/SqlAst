package de.takeweiland.sqlast

import de.takeweiland.sqlast.util.toHexString
import de.takeweiland.sqlast.visitor.DataTypeVisitor
import de.takeweiland.sqlast.visitor.ExpressionVisitor
import de.takeweiland.sqlast.visitor.ValueVisitor

sealed class Expr {

    abstract fun accept(visitor: ExpressionVisitor)

    data class Identifier(val ident: String) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitIdentifier(this)
    }

    object Wildcard : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitWildcard()
    }

    data class QualifiedWildcard(val ident: List<String>) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitQualifiedWildcard(this)
    }

    data class CompoundIdentifier(val ident: List<String>) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitCompoundIdentifier(this)
    }

    data class IsNull(val expr: Expr) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitIsNull(this)
    }

    data class IsNotNull(val expr: Expr) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitIsNotNull(this)
    }

    data class InList(val expr: Expr, val list: List<Expr>, val negated: Boolean = false) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitInList(this)
    }

    data class InSubquery(val expr: Expr, val subquery: Query, val negated: Boolean = false) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitInSubquery(this)
    }

    data class Between(val expr: Expr, val low: Expr, val high: Expr, val negated: Boolean = false) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitBetween(this)
    }

    data class BinaryOp(val left: Expr, val op: BinaryOperator, val right: Expr) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitBinaryOp(this)
    }

    data class UnaryOp(val op: UnaryOperator, val expr: Expr) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitUnaryOp(this)
    }

    data class Cast(val expr: Expr, val dataType: DataType) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitCast(this)
    }

    data class Extract(val expr: Expr, val field: DateTimeField) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitExtract(this)
    }

    data class Collate(val expr: Expr, val collation: String) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitCollate(this)
    }

    data class Nested(val expr: Expr) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitNested(this)
    }

    data class Function(
        val name: ObjectName,
        val args: List<Expr>,
        // TODO: OVER, distinct
    ) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitFunction(this)
    }

    data class Case(val subject: Expr?, val branches: List<Branch>, val elseExpr: Expr?) : Expr() {
        data class Branch(val condition: Expr, val result: Expr)

        override fun accept(visitor: ExpressionVisitor) = visitor.visitCase(this)
    }

    data class Exists(val query: Query) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitExists(this)
    }

    data class Subquery(val query: Query) : Expr() {
        override fun accept(visitor: ExpressionVisitor) = visitor.visitSubquery(this)
    }

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

    final override fun accept(visitor: ExpressionVisitor) = accept(visitor as ValueVisitor)
    abstract fun accept(visitor: ValueVisitor)

    data class Boolean(val value: kotlin.Boolean) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitBoolean(this)
    }

    data class Int(val value: kotlin.Int) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitInt(this)
    }

    data class Long(val value: kotlin.Long) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitLong(this)
    }

    data class Double(val value: kotlin.Double) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitDouble(this)
    }

    data class Number(val value: BigDecimal) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitNumber(this)
    }

    data class String(val value: kotlin.String) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitString(this)
    }

    data class NString(val value: kotlin.String) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitNString(this)
    }

    data class HexString(val value: kotlin.String) : Value() {
        constructor(value: ByteArray) : this(value.toHexString())
        override fun accept(visitor: ValueVisitor) = visitor.visitHexString(this)
    }

    data class Date(val value: LocalDate) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitDate(this)
    }
    data class Time(val value: LocalTime) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitTime(this)
    }
    data class Timestamp(val date: LocalDate, val time: LocalTime) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitTimestamp(this)
    }
    data class TimestampWithTimezone(val date: LocalDate, val time: LocalTime, val timeZone: kotlin.String) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitTimestampWithTimezone(this)
    }
    data class Interval(val value: PeriodDuration) : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitInterval(this)
    }
    object Null : Value() {
        override fun accept(visitor: ValueVisitor) = visitor.visitNull()
    }

}

sealed class DataType {

    abstract fun accept(visitor: DataTypeVisitor)

    data class Char(val length: Long?) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitChar(this)
    }
    data class Varchar(val length: Long) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitVarchar(this)
    }
    object UUID : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitUUID()
    }
    data class Clob(val length: Long) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitClob(this)
    }
    data class Binary(val length: Long) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitBinary(this)
    }
    data class Varbinary(val length: Long) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitVarbinary(this)
    }
    data class Blob(val length: Long) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitBlob(this)
    }
    data class Decimal(val precision: Long?, val scale: Long?) : DataType() {
        init {
            require(precision != null || scale == null) { "Cannot specify scale without precision" }
        }
        override fun accept(visitor: DataTypeVisitor) = visitor.visitDecimal(this)
    }
    data class Numeric(val precision: Long?, val scale: Long?) : DataType() {
        init {
            require(precision != null || scale == null) { "Cannot specify scale without precision" }
        }
        override fun accept(visitor: DataTypeVisitor) = visitor.visitNumeric(this)
    }
    data class Float(val precision: Long?) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitFloat(this)
    }
    object SmallInt : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitSmallInt()
    }
    object Int : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitInt()
    }
    object BigInt : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitBigInt()
    }
    object Real : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitReal()
    }
    object Double : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitDouble()
    }
    object Boolean : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitBoolean()
    }
    object Date : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitDate()
    }
    object Time : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitTime()
    }
    object Timestamp : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitTimestamp()
    }
    object TimestampWithTimezone : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitTimestampWithTimezone()
    }
    object Interval : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitInterval()
    }
    object Text : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitText()
    }
    data class Custom(val identifier: String) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitCustom(this)
    }
    data class Array(val elementType: DataType) : DataType() {
        override fun accept(visitor: DataTypeVisitor) = visitor.visitArray(this)
    }
}