package com.example.routing

import com.example.database.DatabaseFactory
import com.example.models.User
import com.example.models.UserSession
import com.example.tables.RegistationTable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

fun Route.loginSession(){
    route("/loginSession"){
        post {
            val login = call.receive<User>()
            val result = DatabaseFactory.dbQuery {
                RegistationTable.select {
                    RegistationTable.userName.eq(login.userName) and
                            RegistationTable.password.eq(login.password)
                }.map { rowToRegistation(it) }.singleOrNull()
            }
            if (result!=null) {
                call.sessions.set(UserSession(login.userName)); call.respond("LOGGED IN SUCCESSFULLY")
            }else{
                call.respond("INCORRECT CREDENTIALS")
            }
        }
        get ("/logout"){
            call.sessions.clear<UserSession>()
            call.respond("LOGGED OUT SUCCESSFULLY")
        }
    }
}