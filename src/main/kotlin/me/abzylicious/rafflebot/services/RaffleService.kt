package me.abzylicious.rafflebot.services

import me.abzylicious.rafflebot.extensions.jda.getMessageById
import me.abzylicious.rafflebot.extensions.jda.getReaction
import me.abzylicious.rafflebot.extensions.jda.getUsers
import me.abzylicious.rafflebot.extensions.stdlib.toChannel
import me.abzylicious.rafflebot.persistence.Raffle
import me.abzylicious.rafflebot.persistence.RaffleRepository
import me.abzylicious.rafflebot.utilities.Randomizer
import me.jakejmattson.kutils.api.Discord
import me.jakejmattson.kutils.api.annotations.Service
import me.jakejmattson.kutils.api.services.PersistenceService
import net.dv8tion.jda.api.entities.User

data class Winner(val id: String, val name: String)

@Service
class RaffleService(discord: Discord, persistenceService: PersistenceService) {

    private val repository: RaffleRepository = RaffleRepository(discord, persistenceService)
    private val randomizer: Randomizer<User> = Randomizer()

    fun addRaffle(channelId: String, messageId: String, reaction: String): Boolean {
        val exists = repository.exists(messageId)
        if (!exists)
            repository.add(Raffle(channelId, messageId, reaction))

        return !exists
    }

    fun resolveRaffle(messageId: String, winnerCount: Int = 1): List<Winner> {
        val exists = repository.exists(messageId)
        if (!exists)
            return listOf()

        val raffle = repository.get(messageId)!!
        val participants = getRaffleParticipants(raffle).filter { !it.isBot }
        val winnerPool = randomizer.selectRandom(participants, winnerCount)
        return winnerPool.map { Winner(it.id, it.name) }
    }

    private fun getRaffleParticipants(raffle: Raffle): List<User> {
        val channel = raffle.ChannelId.toChannel() ?: return emptyList()
        val message = channel.getMessageById(raffle.MessageId)
        val reaction = message.getReaction(raffle.Reaction)
        return reaction.getUsers()
    }
}
