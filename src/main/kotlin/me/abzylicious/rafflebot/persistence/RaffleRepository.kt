package me.abzylicious.rafflebot.persistence

import me.jakejmattson.discordkt.api.Discord

class RaffleRepository(private val discord: Discord) {

    private val raffleEntries = loadRaffles()

    private fun loadRaffles() = discord.getInjectionObjects(RaffleEntries::class)
    private fun saveRaffles() = raffleEntries.save()

    fun getAll() = raffleEntries.raffles.toList()
    fun get(messageId: String) = raffleEntries.raffles.find { it.MessageId == messageId }

    fun add(raffle: Raffle) {
        raffleEntries.raffles.add(raffle)
        saveRaffles()
    }

    fun remove(messageId: String) {
        if (raffleEntries.raffles.removeIf { it.MessageId == messageId })
            saveRaffles()
    }

    fun exists(messageId: String) = raffleEntries.raffles.any { it.MessageId == messageId }
}
