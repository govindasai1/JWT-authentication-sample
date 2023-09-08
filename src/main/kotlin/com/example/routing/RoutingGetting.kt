package com.example.routing

import com.example.database.DatabaseFactory
import com.example.models.UserDet
import com.example.models.UserSession
import com.example.tables.UsersTable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

fun Route.gettingAll(){
    route("/gettingAccounts"){
        get{
            call.application.environment.log.info("APPLICATION GET METHOD STARTED :) ")
            val users = DatabaseFactory.dbQuery { UsersTable.selectAll().map { rowToUsers(it) } }
            call.respond(users)
        }
        get("userName"){
            val userName = call.receive<UserSession>()
            val user = DatabaseFactory.dbQuery { UsersTable.select { UsersTable.userName.eq(userName.userName) }.map { rowToUsers(it) }.singleOrNull() }
            if (user!=null)call.respond(user)
            else call.respond("ENTER VALID USER ID")
        }

    }
}
private fun rowToUsers(row: ResultRow?):UserDet?{
    return if(row==null) null
    else UserDet(userName = row[UsersTable.userName], accountNumber = row[UsersTable.accountNumber], balance = row[UsersTable.balance])

}
