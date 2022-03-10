package com.example.database.models

import com.example.database.tables.UserSex
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*


class UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

@Serializable
data class CreateUserModel(
    val diameter: Double,
    val sex: UserSex,
    val name: String
)

@Serializable
data class ResponseUserModel(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val diameter: Double,
    val sex: UserSex,
    val name: String
)

@Serializable
@JvmInline
value class UserId(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID
)
