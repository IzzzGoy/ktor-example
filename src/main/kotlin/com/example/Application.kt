package com.example

import com.example.database.initDatabase
import com.example.database.managers.UserManager
import com.example.database.managers.UserManagerImpl
import com.example.database.mappers.UserMapper
import com.example.database.models.CreateUserModel
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*

fun main() {

    initDatabase()
    val userManager: UserManager = UserManagerImpl(UserMapper())
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }
        routing {
            post("user") {
                call.respond(userManager.createUser(call.receive()))
            }
        }
    }.start(wait = true)
}
