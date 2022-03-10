package com.example.database.mappers

import com.example.database.models.ResponseUserModel
import com.example.database.tables.User


interface IUserMapper: Mapper<User, ResponseUserModel>

class UserMapper : IUserMapper {
    override fun invoke(input: User): ResponseUserModel {
        return ResponseUserModel(
            id = input.id.value,
            name = input.name,
            sex = input.sex,
            diameter = input.penisDiameter
        )
    }
}