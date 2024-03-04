package dev.proptit.messenger.data

data class Message(
    val message: String,
    val idReceiveContact: Int,
    val idSendContact: Int,
    val time: Long
)
