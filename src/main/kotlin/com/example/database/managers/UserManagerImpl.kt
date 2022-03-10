package com.example.database.managers

import com.example.database.mappers.UserMapper
import com.example.database.models.CreateUserModel
import com.example.database.models.ResponseUserModel
import com.example.database.tables.User
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UserManagerImpl(
    private val userMapper: UserMapper
): UserManager {
    override fun createUser(user: CreateUserModel): ResponseUserModel {
        return transaction {
            User.new {
                name = user.name
                penisDiameter = user.diameter
                sex = user.sex
            }.let(userMapper::invoke)
        }
    }

    override fun getUser(user: UUID): ResponseUserModel {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<ResponseUserModel> {
        TODO("Not yet implemented")
    }
}