package me.abzylicious.rafflebot.configuration

import me.jakejmattson.discordkt.api.dsl.Data

data class Configuration(
    val ownerId: String = "insert-owner-id",
    val prefix: String = "raffle!",
    val defaultRaffleReaction: String = "\uD83C\uDF89",
    val guildConfigurations: MutableMap<String, GuildConfiguration> = mutableMapOf()
) : Data("config/config.json") {
    operator fun get(id: String) = guildConfigurations[id]
    fun hasGuildConfig(guildId: String) = guildConfigurations.containsKey(guildId)

    fun setup(guildId: String, prefix: String?, adminRoleId: String, staffRoleId: String,
              loggingChannel: String, defaultRaffleReaction: String?) {
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
    val id: String,
    var prefix: String = "raffle!",
    var adminRole: String,
    var staffRole: String,
    var loggingChannel: String,
    var defaultRaffleReaction: String = "\uD83C\uDF89"
)