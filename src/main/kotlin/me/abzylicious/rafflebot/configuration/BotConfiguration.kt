package me.abzylicious.rafflebot.configuration

import me.jakejmattson.discordkt.api.dsl.Data

data class BotConfiguration(
    val prefix: String = "raffle!",
    val loggingChannel: String = "insert-id",
    val defaultRaffleReaction: String = "\uD83C\uDF89"
) : Data("config/config.json")