package com.example

import com.example.database.initDatabase
import com.example.database.managers.UserManager
import com.example.database.managers.UserManagerImpl
import com.example.database.managers.UserNotFoundException
import com.example.database.mappers.UserMapper
import com.example.database.models.CreateUserModel
import com.example.database.models.ResponseUserModel
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.bkbn.kompendium.core.Kompendium
import io.bkbn.kompendium.core.Notarized.notarizedGet
import io.bkbn.kompendium.core.metadata.ResponseInfo
import io.bkbn.kompendium.core.metadata.method.GetInfo
import io.bkbn.kompendium.core.routes.redoc
import io.bkbn.kompendium.core.routes.swagger
import io.bkbn.kompendium.oas.OpenApiSpec
import io.bkbn.kompendium.oas.info.Info
import io.bkbn.kompendium.oas.serialization.KompendiumSerializersModule
import io.bkbn.kompendium.oas.server.Server
import io.bkbn.kompendium.swagger.JsConfig
import io.bkbn.kompendium.swagger.SwaggerUI
import io.ktor.application.*
import io.ktor.application.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URI
import java.net.URL
import java.util.*

fun main() {

    initDatabase()
    val userManager: UserManager = UserManagerImpl(UserMapper())
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(ContentNegotiation) {
            json()
        }

        install(StatusPages) {
            exception<UserNotFoundException> {
                call.respond(HttpStatusCode.Forbidden, it.message.orEmpty())
            }
        }

        install(Kompendium) {
            spec = OpenApiSpec(
                info = Info(
                    title = "test Web App Info"
                ),
                servers = mutableListOf(
                    Server(
                        url = URI("http://localhost:8080"),
                        description = "test location"
                    )
                )
            )
            val customSerializer = Json {
                serializersModule = KompendiumSerializersModule.module
                encodeDefaults = true
                explicitNulls = false
            }
            openApiJson = { spec ->
                route("/openapi.json") {
                    get {
                        call.respondText { customSerializer.encodeToString(spec) }
                    }
                }
            }
        }
        install(SwaggerUI) {
            swaggerUrl = "/swagger-ui"
            jsConfig = JsConfig(
                specs = mapOf("v1" to URI("/openapi.json"))
            )
        }

        routing {
            redoc()
            swagger()
            route("user") {
                post {
                    call.respond(userManager.createUser(call.receive()))
                }
                notarizedGet(getAllUsersInfo) {
                    call.respond(userManager.getAll())
                }
                get("{id}") {
                    val id = UUID.fromString(
                        call.parameters["id"]
                    )
                    call.respond(
                        userManager.getUser(id)
                    )
                }


            }
        }
    }.start(wait = true)
}


val getAllUsersInfo = GetInfo<Unit, List<ResponseUserModel>>(
    responseInfo = ResponseInfo(
        status = HttpStatusCode.OK,
        description = "Success"
    ),
    summary = "Get all users",
    tags = setOf("Users")
)