package me.abzylicious.rafflebot.persistence

import me.jakejmattson.kutils.api.annotations.Data

data class Raffle (
    val ChannelId: String,
    val MessageId: String,
    val Reaction: String
)

@Data("data/raffles.json")
data class RaffleEntries(val raffles: MutableList<Raffle> = mutableListOf())