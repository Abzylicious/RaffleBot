package me.abzylicious.rafflebot.configuration

import me.jakejmattson.discordkt.api.dsl.Data

data class Configuration(
    val ownerId: String = "insert-owner-id",
    val prefix: String = "raffle!",
    val defaultRaffleReaction: String = "\uD83C\uDF89",
    val guildConfigurations: MutableMap<Long, GuildConfiguration> = mutableMapOf()
) : Data("config/config.json") {
    operator fun get(id: Long) = guildConfigurations[id]
    fun hasGuildConfig(guildId: Long) = guildConfigurations.containsKey(guildId)

    fun setup(guildId: Long, prefix: String?, adminRoleId: Long, staffRoleId: Long,
              loggingChannel: Long, defaultRaffleReaction: String?) {
        if (guildConfigurations[guildId] != null) return

        val newGuildConfiguration = GuildConfiguration(
            guildId,
            prefix ?: this.prefix,
            adminRoleId,
            staffRoleId,
            loggingChannel,
            defaultRaffleReaction ?: this.defaultRaffleReaction
        )
        guildConfigurations[guildId] = newGuildConfiguration
        save()
    }
}

data class GuildConfiguration(
    val id: Long,
    var prefix: String = "raffle!",
    var adminRole: Long,
    var staffRole: Long,
    var loggingChannel: Long,
    var defaultRaffleReaction: String = "\uD83C\uDF89"
)