package me.abzylicious.rafflebot.configuration

import me.jakejmattson.kutils.api.annotations.Data

@Data("config/config.json")
data class BotConfiguration(
    val prefix: String = "raffle!"
)