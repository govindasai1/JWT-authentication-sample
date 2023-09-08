package com.example.plugins

import com.example.models.Amount
import com.example.models.RegReceive
import com.example.models.UserSession
import com.fasterxml.jackson.databind.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.slf4j.event.Level

fun Application.configureSerialization() {
    install(RequestValidation){
        validate<RegReceive>{
            if (it.userName.length in 1..9 && it.gmail.endsWith("@gmail.com")) ValidationResult.Valid
            else
                ValidationResult.Invalid("INVALID CREDENTIALS")
        }
        validate<Amount> {
            if(it.balance>0) ValidationResult.Valid
            else ValidationResult.Invalid("ENTER A AMOUNT THAT IS POSITIVE")
        }
    }
    install(Sessions){
        cookie<UserSession>("userSession")
    }
    install(CallLogging){
        level = Level.WARN
        filter { call ->
            call.request.path().endsWith("/gettingAccounts")
        }
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            "Status : $status  Method: $httpMethod "
        }
    }
    install(ContentNegotiation) {
        jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
    }
    routing {
        get("/json/jackson") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
