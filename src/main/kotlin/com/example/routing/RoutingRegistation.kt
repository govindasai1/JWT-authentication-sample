package com.example.routing

import com.example.database.DatabaseFactory
import com.example.models.RegReceive
import com.example.tables.RegistationTable
import com.example.tables.UsersTable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert

fun Route.registationnRouting(){
    route("/registation"){
        post{
            val registation = call.receive<RegReceive>()
            val result2 = DatabaseFactory.dbQuery {
                val random = (1..50).random()
                val rm2: Int = random * 10 + 215
                UsersTable.insert {
                    it[userName] = registation.userName
                    it[accountNumber] =rm2
                    it[balance] = 0.0
                }.resultedValues?.singleOrNull()
            }
           val result= DatabaseFactory.dbQuery {
               RegistationTable.insert {
                   it[userName] = registation.userName
                   it[password] = registation.password
                   it[gmail] = registation.gmail
               }.resultedValues?.singleOrNull()

           }


            if(result!=null && result2!=null) call.respond("REGISTATION SUCCESSFUL")
            else call.respond("NOT REGISTERED")

        }
    }
}