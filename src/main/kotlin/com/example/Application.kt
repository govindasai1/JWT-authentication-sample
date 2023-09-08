package com.example

import com.example.database.DatabaseFactory
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 5055, host = "127.0.1.8", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    log.info("hello world happy coding")
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
}
