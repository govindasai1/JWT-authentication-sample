package com.example.database

import com.example.tables.RegistationTable
import com.example.tables.UsersTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val url = "jdbc:postgresql://localhost:8080/Bank"
        val driver = "org.postgresql.Driver"
        val user = "postgres"
        val password = "root"
        Database.connect(url, driver, user, password)

        transaction {
            SchemaUtils.createMissingTablesAndColumns(RegistationTable,UsersTable)
        }
    }

    suspend fun <T> dbQuery(block:  () ->T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
}