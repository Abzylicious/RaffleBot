package me.abzylicious.rafflebot.configuration

import me.jakejmattson.kutils.api.annotations.Data

@Data("config/messages.json")
data class Messages(
    val STARTUP_LOG: String = "Bot successfully initialized!"
)