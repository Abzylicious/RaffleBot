package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.conversations.ConfigurationConversation
import me.abzylicious.rafflebot.embeds.createConfigurationEmbed
import me.abzylicious.rafflebot.extensions.discordkt.getEmoteIdOrValue
import me.abzylicious.rafflebot.extensions.stdlib.toDisplayableEmote
import me.abzylicious.rafflebot.services.PermissionLevel
import me.abzylicious.rafflebot.services.requiredPermissionLevel
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.commands

fun configurationCommands(configuration: Configuration, messages: Messages) = commands("Configuration") {
    guildCommand("configuration") {
        description = "Show the current guild configuration"
        requiredPermissionLevel = PermissionLevel.Staff
        execute {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val guildConfiguration = configuration[guildId]!!
            respond { createConfigurationEmbed(discord, guild, guildConfiguration) }
        }
    }

    guildCommand("configure") {
        description = "Configure a guild to use this bot"
        requiredPermissionLevel = PermissionLevel.Administrator
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

    guildCommand("setprefix") {
        description = "Set the bot prefix"
        requiredPermissionLevel = PermissionLevel.Administrator
        execute(EveryArg) {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val prefix = args.first
            configuration[guildId]?.prefix = prefix
            configuration.save()
            respond("Prefix set to **$prefix**")
        }
    }

    guildCommand("setadminrole") {
        description = "Set the bot admin role"
        requiredPermissionLevel = PermissionLevel.Administrator
        execute(RoleArg) {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val role = args.first
            configuration[guildId]?.adminRole = role.id.longValue
            configuration.save()
            respond("Role set to: **${role.name}**")
        }
    }

    guildCommand("setstaffrole") {
        description = "Set the bot staff role"
        requiredPermissionLevel = PermissionLevel.Administrator
        execute(RoleArg) {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val role = args.first
            configuration[guild.id.longValue]?.staffRole = role.id.longValue
            configuration.save()
            respond("Role set to: **${role.name}**")
        }
    }

    guildCommand("setloggingchannel") {
        description = "Set the bot logging channel"
        requiredPermissionLevel = PermissionLevel.Administrator
        execute(ChannelArg) {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val channel = args.first
            configuration[guild.id.longValue]?.loggingChannel = channel.id.longValue
            configuration.save()
            respond("Channel set to: **${channel.name}**")
        }
    }

    guildCommand("setdefaultreaction") {
        description = "Set the default reaction for raffles"
        requiredPermissionLevel = PermissionLevel.Administrator
        execute(EitherArg(GuildEmojiArg, UnicodeEmojiArg)) {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val reaction = args.first.getEmoteIdOrValue()
            configuration[guild.id.longValue]?.defaultRaffleReaction = reaction
            configuration.save()
            respond("Reaction set to: ${reaction.toDisplayableEmote(guildId)}")
        }
    }
}
