package me.abzylicious.rafflebot.persistence

import me.jakejmattson.kutils.api.Discord
import me.jakejmattson.kutils.api.services.PersistenceService

class RaffleRepository(private val discord: Discord,
                       private val persistence: PersistenceService) {

    private val raffleEntries = loadRaffles()

    private fun loadRaffles() = discord.getInjectionObjects(RaffleEntries::class)
    private fun saveRaffles() = persistence.save(raffleEntries)

    fun getAll() = raffleEntries.raffles.toList()
    fun get(messageId: String) = raffleEntries.raffles.find { it.MessageId == messageId }

    fun add(raffle: Raffle) {
        raffleEntries.raffles.add(raffle)
        saveRaffles()
    }

    fun remove(messageId: String) = raffleEntries.raffles.removeIf { it.MessageId == messageId }
    fun exists(messageId: String) = raffleEntries.raffles.any { it.MessageId == messageId }
}
