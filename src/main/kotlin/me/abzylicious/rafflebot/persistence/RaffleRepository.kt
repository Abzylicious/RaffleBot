package me.abzylicious.rafflebot.persistence

import me.jakejmattson.discordkt.api.Discord

class RaffleRepository(private val discord: Discord) {

    private val raffleEntries = loadRaffles()

    private fun loadRaffles() = discord.getInjectionObjects(RaffleEntries::class)
    private fun saveRaffles() = raffleEntries.save()

    fun getAll(guildId: String) = raffleEntries.raffles.filter { it.GuildId == guildId }
    fun get(guildId: String, messageId: String) = raffleEntries.raffles.find { it.GuildId == guildId && it.MessageId == messageId }

    fun add(raffle: Raffle) {
        raffleEntries.raffles.add(raffle)
        saveRaffles()
    }

    fun remove(guildId: String, messageId: String) {
        if (raffleEntries.raffles.removeIf { it.GuildId == guildId && it.MessageId == messageId })
            saveRaffles()
    }

    fun clear(guildId: String) {
        if (raffleEntries.raffles.removeIf {it.GuildId == guildId })
            saveRaffles()
    }

    fun exists(guildId: String) = raffleEntries.raffles.any { it.GuildId == guildId }
    fun exists(guildId: String, messageId: String) = raffleEntries.raffles.any { it.GuildId == guildId && it.MessageId == messageId }
}
