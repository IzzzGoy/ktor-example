package com.example.database

import com.example.database.tables.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.Driver
import kotlin.reflect.jvm.jvmName

fun initDatabase() {
    val url         = System.getenv("BD_URL") ?: "jdbc:postgresql://localhost:5432/example"
    val user        = System.getenv("BD_USER") ?: "root"
    val password    = System.getenv("BD_PASSWORD") ?: "example"

    Database.connect(
        url = url,
        user = user,
        password = password,
        driver = Driver::class.jvmName
    )

    transaction {
        SchemaUtils.createMissingTablesAndColumns(
            Users
        )
    }
}