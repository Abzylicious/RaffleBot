package me.abzylicious.rafflebot.configuration

import me.jakejmattson.kutils.api.annotations.Data

@Data("config/messages.json")
data class Messages(
    val NO_TOKEN_PROVIDED: String = "Please specify a bot token",
    val STARTUP_LOG: String = "Bot successfully initialized!",
    val RAFFLE_EXISTS: String = "This raffle already exists",
    val MESSAGE_CONVERT_SUCCESS: String = "Message successfully converted and under my watch now"
)