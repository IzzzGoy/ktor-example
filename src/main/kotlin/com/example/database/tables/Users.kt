package com.example.database.tables

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

enum class UserSex {
    MALE, FEMALE, UNKNOWN
}

object Users : UUIDTable() {
    val penisDiameter = double("user_penis_diameter")
    val sex = enumeration("user_sex", UserSex::class)
    val name = varchar("user_name" , 32)
}

class User(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: EntityClass<UUID, User>(Users)

    var penisDiameter by Users.penisDiameter
    var sex by Users.sex
    var name by Users.name
}