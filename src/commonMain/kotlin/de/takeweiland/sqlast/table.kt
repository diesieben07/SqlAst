package de.takeweiland.sqlast

sealed class TableReference

sealed class TableFactor : TableReference() {

    data class Table(val name: ObjectName, val alias: TableAlias?) : TableFactor()
    data class Derived(val subquery: Query, val alias: String?): TableFactor()

}

data class JoinedTable(
    val table: TableReference,
    val operator: JoinOperator,
    val specification: JoinSpecification?
) : TableReference()

data class TableAlias(val alias: String, val colList: List<String>)

enum class JoinOperator {

    INNER,
    CROSS,
    LEFT_OUTER,
    RIGHT_OUTER

}

sealed class JoinSpecification {

    object Natural : JoinSpecification()
    data class On(val expr: Expr) : JoinSpecification()
    data class Using(val columns: List<Ident>) : JoinSpecification()

}

data class Cte(val query: Query, val alias: TableAlias)

sealed class SetExpr

data class Select(
    val distinct: Boolean,
    val items: List<Item>,
    val from: List<TableReference>,
    val where: Expr?,
    val groupBy: List<Expr>,
    val having: Expr?
): SetExpr() {

    data class Item(val expr: Expr, val alias: String?)

}

enum class SetOperator {
    UNION,
    EXCEPT,
    INTERSECT
}

data class SetOperation(
    val op: SetOperator,
    val all: Boolean,
    val left: SetExpr,
    val right: SetExpr
) : SetExpr()

data class Values(val values: List<List<Expr>>) : SetExpr()

data class Query(
    val ctes: List<Cte>,
    val body: SetExpr,
    val orderBy: List<OrderByExpr>,
    val limit: Expr?,
    val offset: Expr?

) : SetExpr()

enum class Order {
    ASC,
    DESC
}

data class OrderByExpr(val expr: Expr, val order: Order)