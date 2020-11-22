package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.conversations.ConfigurationConversation
import me.jakejmattson.discordkt.api.dsl.commands

fun configurationCommands(configuration: Configuration, messages: Messages) = commands("Configuration") {
    guildCommand("configure") {
        description = "Configure a guild to use this bot"
        execute {
            val guildId = guild.id.longValue
            if (configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_EXISTS)
                return@execute
            }

            ConfigurationConversation(configuration, messages)
                .createConfigurationConversation(guildId)
                .startPublicly(discord, author, channel)

            respond("**${guild.name}** ${messages.SETUP_COMPLETE}")
        }
    }
}
