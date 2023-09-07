package com.example.plugins

import com.example.models.RegReceive
import com.fasterxml.jackson.databind.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {
    install(RequestValidation){
        validate<RegReceive>{
            if (it.userName.length in 1..9 && it.gmail.endsWith("@gmail.com")) ValidationResult.Valid
            else
                ValidationResult.Invalid("INVALID CREDENTIALS")
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
