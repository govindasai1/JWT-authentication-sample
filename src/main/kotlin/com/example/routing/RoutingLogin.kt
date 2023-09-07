package com.example.routing

import com.example.database.DatabaseFactory
import com.example.models.Register
import com.example.models.User
import com.example.tables.RegistationTable
import com.example.utils.TokenManager
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select



fun Route.loginRouting(){
    val tokenManager = TokenManager()

    route("/login"){
        post{
            val login = call.receive<User>()
            val result = DatabaseFactory.dbQuery {
                RegistationTable.select {
                    RegistationTable.userName.eq(login.userName) and
                    RegistationTable.password.eq(login.password)
                }.map { rowToRegistation(it) }.singleOrNull()
            }
            if(result==null) call.respond("NOT LOGGED IN")
            else {
                val token = tokenManager.generateJWTToken(result)
                call.respond(token)
            }
        }


    }
}
fun rowToRegistation(row:ResultRow?):Register?{
    return if (row==null) null
    else
        Register(id = row[RegistationTable.id], userName = row[RegistationTable.userName], password = row[RegistationTable.password], gmail = row[RegistationTable.gmail])
}