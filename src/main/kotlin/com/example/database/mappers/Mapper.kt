package com.example.database.mappers

interface Mapper<in INPUT, out OUTPUT> {
    operator fun invoke(input: INPUT): OUTPUT
}