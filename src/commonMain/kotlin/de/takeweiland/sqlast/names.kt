package de.takeweiland.sqlast

typealias Ident = String
inline class ObjectName(val names: List<Ident>)