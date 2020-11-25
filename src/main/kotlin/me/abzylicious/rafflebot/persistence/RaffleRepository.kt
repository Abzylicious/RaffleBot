package me.abzylicious.rafflebot.persistence

import me.jakejmattson.discordkt.api.Discord

class RaffleRepository(private val discord: Discord) {

    private val raffleEntries = loadRaffles()

    private fun loadRaffles() = discord.getInjectionObjects(RaffleEntries::class)
    private fun saveRaffles() = raffleEntries.save()

    fun getAll(guildId: Long) = raffleEntries.raffles.filter { it.GuildId == guildId }
    fun get(guildId: Long, messageId: Long) = raffleEntries.raffles.find { it.GuildId == guildId && it.MessageId == messageId }

    fun add(raffle: Raffle) {
        raffleEntries.raffles.add(raffle)
        saveRaffles()
    }

    fun remove(guildId: Long, messageId: Long) {
        if (raffleEntries.raffles.removeIf { it.GuildId == guildId && it.MessageId == messageId })
            saveRaffles()
    }

    fun clear(guildId: Long) {
        if (raffleEntries.raffles.removeIf {it.GuildId == guildId })
            saveRaffles()
    }

    fun exists(guildId: Long) = raffleEntries.raffles.any { it.GuildId == guildId }
    fun exists(guildId: Long, messageId: Long) = raffleEntries.raffles.any { it.GuildId == guildId && it.MessageId == messageId }
}
