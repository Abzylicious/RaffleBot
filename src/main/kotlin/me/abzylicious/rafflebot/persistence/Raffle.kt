package me.abzylicious.rafflebot.persistence

import me.jakejmattson.discordkt.api.dsl.Data

data class Raffle (
    val ChannelId: String,
    val MessageId: String,
    val Reaction: String
)

data class RaffleEntries(val raffles: MutableList<Raffle> = mutableListOf()) : Data("data/raffles.json")