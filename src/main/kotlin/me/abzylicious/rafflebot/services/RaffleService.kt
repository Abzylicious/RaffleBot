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

data class Winner(val id: String, val name: String, val mention: String)

@Service
class RaffleService(discord: Discord) {

    private val repository: RaffleRepository = RaffleRepository(discord)
    private val randomizer: Randomizer<User> = Randomizer()

    fun rafflesExist(guildId: Long) = repository.exists(guildId)
    fun raffleExists(guildId: Long, messageId: Long) = repository.exists(guildId, messageId)
    fun getRaffles(guildId: Long) = repository.getAll(guildId)

    fun addRaffle(guildId: Long, messageId: Long, channelId: Long, reaction: String, messageUrl: String) {
        if (!raffleExists(guildId, messageId))
            repository.add(Raffle(guildId, messageId, channelId, reaction, messageUrl))
    }

    fun removeRaffle(guildId: Long, messageId: Long) = repository.remove(guildId, messageId)
    fun clearRaffles(guildId: Long) = repository.clear(guildId)

    suspend fun resolveRaffle(guildId: Long, messageId: Long, winnerCount: Int = 1): List<Winner> {
        if (!raffleExists(guildId, messageId))
            return listOf()

        val raffle = repository.get(guildId, messageId)!!
        val participants = getRaffleParticipants(raffle).filter { it.isBot == null || it.isBot == false }
        return randomizer.selectRandom(participants, winnerCount).map { Winner(it.id.value, it.tag, it.mention) }
    }

    private suspend fun getRaffleParticipants(raffle: Raffle): List<User> {
        val channel = raffle.ChannelId.toTextChannel() ?: return emptyList()
        val message = channel.getMessage(raffle.MessageId.toSnowflake())
        val reaction = message.getReaction(raffle.Reaction)
        return if (reaction != null) message.getReactors(reaction).toList() else listOf()
    }
}
