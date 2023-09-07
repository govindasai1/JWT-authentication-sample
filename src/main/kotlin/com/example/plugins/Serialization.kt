package com.example.plugins

import com.example.models.Amount
import com.example.models.RegReceive
import com.example.models.UserSession
import com.fasterxml.jackson.databind.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

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
