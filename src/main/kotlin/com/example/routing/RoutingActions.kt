package com.example.routing

import com.example.database.DatabaseFactory
import com.example.models.Balance
import com.example.tables.UsersTable
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.update

fun Route.actionRouting(){
    route("/action"){
        authenticate {
            get("/deposit") {
                val principal = call.principal<JWTPrincipal>()
                val balance = call.receive<Balance>()
                val userName = principal!!.payload.getClaim("userName").asString()
                val result = DatabaseFactory.dbQuery {
                    UsersTable.update({UsersTable.userName.eq(userName)}){
                        it[this.balance] = this.balance+balance.balance
                    }
                }
                if(result==1) call.respond("after depositing $userName balance is $balance")
                else call.respond("not deposited")

            }
        }
    }
}