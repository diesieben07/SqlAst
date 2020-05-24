package de.takeweiland.sqlast.visitor

import de.takeweiland.sqlast.*

interface SetExprVisitor {
    fun visitValues(expr: Values)
    fun visitQuery(query: Query)
    fun visitSetOperation(operation: SetOperation)
}