package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.conversations.ConfigurationConversation
import me.abzylicious.rafflebot.embeds.createConfigurationEmbed
import me.abzylicious.rafflebot.extensions.discordkt.getEmoteIdOrValue
import me.abzylicious.rafflebot.extensions.discordkt.log
import me.abzylicious.rafflebot.extensions.stdlib.toDisplayableEmote
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.commands

fun configurationCommands(configuration: Configuration, messages: Messages) = commands("Configuration") {
    guildCommand("configuration") {
        description = "Show the current guild configuration"
        execute {
            val guildId = guild.id.longValue
            if (!configuration.hasGuildConfig(guildId)) {
                respond(messages.GUILD_CONFIGURATION_NOT_FOUND)
                return@execute
            }

            val guildConfiguration = configuration[guildId]!!
            respond { createConfigurationEmbed(discord, guild, guildConfiguration) }
            log(configuration, guildId, names)
        }
    }

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
            log(configuration, guildId, names)
        }
    }

    guildCommand("setprefix") {
        description = "Set the bot prefix"
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
            log(configuration, guildId, names)
        }
    }

    guildCommand("setadminrole") {
        description = "Set the bot admin role"
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
            log(configuration, guildId, names)
        }
    }

    guildCommand("setstaffrole") {
        description = "Set the bot staff role"
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
            log(configuration, guildId, names)
        }
    }

    guildCommand("setloggingchannel") {
        description = "Set the bot logging channel"
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
            log(configuration, guildId, names)
        }
    }

    guildCommand("setdefaultreaction") {
        description = "Set the default reaction for raffles"
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
            log(configuration, guildId, names)
        }
    }
}