package com.example.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
object RegistationTable :Table(){
    val id:Column<Int> = integer("id").autoIncrement()
    val userName:Column<String> = varchar("userName",252)
    val password:Column<String> = varchar("password",252)
    val gmail:Column<String> = varchar("gmail",252)
    override val primaryKey = PrimaryKey(id)
}