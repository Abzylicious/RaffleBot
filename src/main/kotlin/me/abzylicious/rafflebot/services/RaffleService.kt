package me.abzylicious.rafflebot.services

import com.gitlab.kordlib.core.entity.User
import kotlinx.coroutines.flow.toList
import me.abzylicious.rafflebot.extensions.kord.getReaction
import me.abzylicious.rafflebot.extensions.stdlib.toTextChannel
import me.abzylicious.rafflebot.persistence.Raffle
import me.abzylicious.rafflebot.persistence.RaffleRepository
import me.abzylicious.rafflebot.utilities.Randomizer
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import me.jakejmattson.discordkt.api.extensions.toSnowflake

data class Winner(val id: String, val name: String)

@Service
class RaffleService(discord: Discord) {

    private val repository: RaffleRepository = RaffleRepository(discord)
    private val randomizer: Randomizer<User> = Randomizer()

    fun addRaffle(channelId: String, messageId: String, reaction: String): Boolean {
        val exists = repository.exists(messageId)
        if (!exists)
            repository.add(Raffle(channelId, messageId, reaction))

        return !exists
    }

    suspend fun resolveRaffle(messageId: String, winnerCount: Int = 1): List<Winner> {
        val exists = repository.exists(messageId)
        if (!exists)
            return listOf()

        val raffle = repository.get(messageId)!!
        val participants = getRaffleParticipants(raffle).filter { !it.isBot!! }
        val winnerPool = randomizer.selectRandom(participants, winnerCount)
        return winnerPool.map { Winner(it.id.value, it.username) }
    }

    private suspend fun getRaffleParticipants(raffle: Raffle): List<User> {
        val channel = raffle.ChannelId.toTextChannel() ?: return emptyList()
        val message = channel.getMessage(raffle.MessageId.toSnowflake())
        val reaction = message.getReaction(raffle.Reaction)
        return message.getReactors(reaction).toList()
    }
}
