package de.takeweiland.sqlast.visitor

import de.takeweiland.sqlast.DataType
import de.takeweiland.sqlast.Expr
import de.takeweiland.sqlast.Value

interface ValueVisitor {

    fun visitBoolean(expr: Value.Boolean)
    fun visitInt(expr: Value.Int)
    fun visitLong(expr: Value.Long)
    fun visitDouble(expr: Value.Double)
    fun visitNumber(expr: Value.Number)
    fun visitString(expr: Value.String)
    fun visitNString(expr: Value.NString)
    fun visitHexString(expr: Value.HexString)
    fun visitDate(expr: Value.Date)
    fun visitTime(expr: Value.Time)
    fun visitTimestamp(expr: Value.Timestamp)
    fun visitTimestampWithTimezone(expr: Value.TimestampWithTimezone)
    fun visitInterval(expr: Value.Interval)
    fun visitNull()

}

interface ExpressionVisitor : ValueVisitor {

    fun visitIdentifier(expr: Expr.Identifier)
    fun visitWildcard()
    fun visitQualifiedWildcard(expr: Expr.QualifiedWildcard)
    fun visitCompoundIdentifier(expr: Expr.CompoundIdentifier)
    fun visitIsNull(expr: Expr.IsNull)
    fun visitIsNotNull(expr: Expr.IsNotNull)
    fun visitInList(expr: Expr.InList)
    fun visitInSubquery(expr: Expr.InSubquery)
    fun visitBetween(expr: Expr.Between)
    fun visitBinaryOp(expr: Expr.BinaryOp)
    fun visitUnaryOp(expr: Expr.UnaryOp)
    fun visitCast(expr: Expr.Cast)
    fun visitExtract(expr: Expr.Extract)
    fun visitCollate(expr: Expr.Collate)
    fun visitNested(expr: Expr.Nested)
    fun visitFunction(expr: Expr.Function)
    fun visitCase(expr: Expr.Case)
    fun visitExists(expr: Expr.Exists)
    fun visitSubquery(expr: Expr.Subquery)

}

interface DataTypeVisitor {
    fun visitChar(type: DataType.Char)
    fun visitVarchar(type: DataType.Varchar)
    fun visitUUID()
    fun visitClob(type: DataType.Clob)
    fun visitBinary(type: DataType.Binary)
    fun visitVarbinary(type: DataType.Varbinary)
    fun visitBlob(type: DataType.Blob)
    fun visitDecimal(type: DataType.Decimal)
    fun visitNumeric(type: DataType.Numeric)
    fun visitFloat(type: DataType.Float)
    fun visitSmallInt()
    fun visitInt()
    fun visitBigInt()
    fun visitReal()
    fun visitDouble()
    fun visitBoolean()
    fun visitDate()
    fun visitTime()
    fun visitTimestamp()
    fun visitTimestampWithTimezone()
    fun visitInterval()
    fun visitText()
    fun visitCustom(type: DataType.Custom)
    fun visitArray(type: DataType.Array)
}