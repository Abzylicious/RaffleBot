package me.abzylicious.rafflebot.persistence

import me.jakejmattson.kutils.api.Discord
import me.jakejmattson.kutils.api.annotations.Service
import me.jakejmattson.kutils.api.services.PersistenceService

@Service
class RaffleRepository(private val discord: Discord,
                       private val persistence: PersistenceService,
                       private var raffleEntries: RaffleEntries) {
    init {
        raffleEntries = loadRaffles()
    }

    private fun loadRaffles() = discord.getInjectionObjects(RaffleEntries::class)
    private fun saveRaffles() = persistence.save(raffleEntries)

    fun addRaffle(raffle: Raffle) {
        raffleEntries.raffles.add(raffle)
        saveRaffles()
    }

    fun raffleExists(messageId: String) = raffleEntries.raffles.any { it.MessageId == messageId }
}