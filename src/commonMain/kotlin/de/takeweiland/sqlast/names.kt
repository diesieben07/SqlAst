package de.takeweiland.sqlast

typealias Ident = String
inline class ObjectName(val names: List<Ident>) {
    constructor(vararg names: Ident) : this(names.asList())
    constructor(name: Ident) : this(listOf(name))
}