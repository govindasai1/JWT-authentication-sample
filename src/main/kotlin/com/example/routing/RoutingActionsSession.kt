package com.example.routing

import com.example.database.DatabaseFactory
import com.example.models.Amount
import com.example.models.UserSession
import com.example.tables.UsersTable
import com.example.tables.UsersTable.userName
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.update

fun Route.actionSession(){
    route("/session"){
        post("/deposit") {
            val userSession = call.sessions.get<UserSession>()
            val amount = call.receive<Amount>()
            if (userSession == null) call.respond("USER NOT LOGGED IN")
            else {
                val result = DatabaseFactory.dbQuery {
                    UsersTable.update({ userName.eq(userSession.userName) }) {
                        it[this.balance] = this.balance + amount.balance
                    }
                }
                if (result == 1) call.respond("after depositing $userName deposited amount is $amount")
                else call.respond("not deposited")
            }

        }

    }

}