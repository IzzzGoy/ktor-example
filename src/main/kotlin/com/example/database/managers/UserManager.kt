package com.example.database.managers

import com.example.database.models.CreateUserModel
import com.example.database.models.ResponseUserModel
import java.util.*

interface UserManager {
    fun createUser(user: CreateUserModel): ResponseUserModel
    fun getUser(user: UUID): ResponseUserModel
    fun getAll(): List<ResponseUserModel>
}