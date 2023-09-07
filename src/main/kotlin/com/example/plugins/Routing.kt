package com.example.plugins

import com.example.routing.*
import com.example.routing.actionSession
import com.example.utils.TokenManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val tokenManager = TokenManager()
    install(Authentication){
        jwt {
            verifier(tokenManager.verifyJWTToken())
            tokenManager.realm
            validate { jwtCredential ->
                if (jwtCredential.payload.getClaim("userName").asString().isNotBlank()){
                    JWTPrincipal(jwtCredential.payload)
                }
                else
                    null
            }
        }
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }

    routing {
        loginSession()
        actionSession()
        actionRouting()
        registationnRouting()
        loginRouting()
    }
}
