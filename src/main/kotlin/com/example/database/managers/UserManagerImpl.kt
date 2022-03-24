package com.example.database.managers

import com.example.database.mappers.UserMapper
import com.example.database.models.CreateUserModel
import com.example.database.models.ResponseUserModel
import com.example.database.tables.User
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


class UserNotFoundException(uuid: UUID): Exception("User with id: $uuid not found")

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
        return transaction {
            User.findById(user) ?: throw UserNotFoundException(user)
        }.let(userMapper::invoke)
    }

    override fun getAll(): List<ResponseUserModel> {
        return transaction { User.all().map(userMapper::invoke) }
    }
}