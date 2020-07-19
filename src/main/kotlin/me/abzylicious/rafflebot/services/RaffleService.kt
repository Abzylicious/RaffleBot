package me.abzylicious.rafflebot.services

import me.abzylicious.rafflebot.persistence.Raffle
import me.abzylicious.rafflebot.persistence.RaffleRepository
import me.jakejmattson.kutils.api.Discord
import me.jakejmattson.kutils.api.annotations.Service
import me.jakejmattson.kutils.api.services.PersistenceService

@Service
class RaffleService(private val discord: Discord,
                    private val persistenceService: PersistenceService,
                    private val repository: RaffleRepository = RaffleRepository(discord, persistenceService)) {

    fun addRaffle(channelId: String, messageId: String, reaction: String): Boolean {
        val exists = repository.exists(messageId)
        if (!exists)
            repository.add(Raffle(channelId, messageId, reaction))

        return !exists
    }


}
