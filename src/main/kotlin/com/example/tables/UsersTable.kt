package com.example.tables

import org.jetbrains.exposed.sql.Table

object UsersTable:Table() {
    private val id = integer("id").autoIncrement()
    val userName = varchar("userName",252)
    val accountNumber = integer("accountNumber")
    val balance = double("balance")
    override val primaryKey = PrimaryKey(id)
}