package me.abzylicious.rafflebot.persistence

import me.jakejmattson.discordkt.api.dsl.Data

data class Raffle (
    val GuildId: Long,
    val MessageId: Long,
    val ChannelId: Long,
    val Reaction: String,
    val MessageUrl: String,
)

data class RaffleEntries(val raffles: MutableList<Raffle> = mutableListOf()) : Data("data/raffles.json", false)