package me.abzylicious.rafflebot.configuration

import me.jakejmattson.kutils.api.annotations.Data

@Data("config/config.json")
data class BotConfiguration(
    val prefix: String = "raffle!",
    val loggingChannel: String = "insert-id",
    val defaultRaffleReaction: String = "\uD83C\uDF89"
)