package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.kord.addReaction
import me.abzylicious.rafflebot.extensions.kord.jumpLink
import me.abzylicious.rafflebot.extensions.stdlib.toDisplayableEmote
import me.abzylicious.rafflebot.services.RaffleService
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.discordkt.api.extensions.addInlineField

fun raffleCommands(config: BotConfiguration, raffleService: RaffleService, messages: Messages) = commands("Raffle") {
    guildCommand("List") {
        description = "Lists all active raffles"
        execute {
            val raffles = raffleService.getRaffles(guild.id.value)
            if (raffles.isEmpty()) {
                respond {
                    title = "Raffles"
                    description = "There are no active raffles currently"
                    thumbnail { url = discord.api.getSelf().avatar.url }
                }

                return@execute
            }

            respond {
                title = "Raffles"
                description = "Currently active raffles"
                thumbnail { url = discord.api.getSelf().avatar.url }
                for (raffle in raffles) {
                    addInlineField("Raffle Id (MessageId)", raffle.MessageId)
                    addInlineField("Message", "[Jump to](${raffle.MessageUrl})")
                    addInlineField("Reaction", raffle.Reaction.toDisplayableEmote(guild.id.value))
                }
            }
        }
    }
    guildCommand("Convert") {
        description = "Converts a message to a raffle"
        execute(MessageArg, EitherArg(GuildEmojiArg, UnicodeEmojiArg).makeNullableOptional()) {
            val guildId = guild.id.value
            val messageId = args.first.id.value

            if (raffleService.raffleExists(guildId, messageId)) {
                respond(messages.RAFFLE_EXISTS)
                return@execute
            }

            val messageUrl = args.first.jumpLink(guild.id.value)
            val channelId = args.first.channelId.value
            val reaction = args.second?.map({ emote -> emote.id.value }, { unicodeEmote -> unicodeEmote.unicode }) ?: config.defaultRaffleReaction

            raffleService.addRaffle(guildId, messageId, channelId, reaction, messageUrl)
            channel.addReaction(guildId, messageId, reaction)
            respond(messages.MESSAGE_CONVERT_SUCCESS)
        }
    }
    guildCommand("End") {
        description = "End a given raffle"
        execute(MessageArg, IntegerArg.makeOptional(1)) {
            val guildId = guild.id.value
            val messageId = args.first.id.value
            val winnerCount = args.second

            if (!raffleService.raffleExists(guildId, messageId)) {
                respond(messages.RAFFLE_NOT_FOUND)
                return@execute
            }

            val winners = raffleService.resolveRaffle(guildId, messageId, winnerCount)
            if (winners.isEmpty()) {
                respond(messages.NO_WINNER_AVAILABLE)
                return@execute
            }

            respond(messages.CONGRATULATION)
            for (winner in winners) {
                respond("${winner.mention} (${winner.name} :: ${winner.id})")
            }

            raffleService.removeRaffle(guildId, messageId)
        }
    }
    guildCommand("Remove") {
        description = "Remove a given raffle"
        execute(MessageArg) {
            val guildId = guild.id.value
            val messageId = args.first.id.value

            if (!raffleService.raffleExists(guildId, messageId)) {
                respond(messages.RAFFLE_NOT_FOUND)
                return@execute
            }

            raffleService.removeRaffle(guildId, messageId)
            respond(messages.RAFFLE_REMOVED)
        }
    }
    guildCommand("Clear") {
        description = "Remove all raffles"
        execute {
            val guildId = guild.id.value

            if (!raffleService.rafflesExist(guildId)) {
                respond(messages.NO_RAFFLES_AVAILABLE)
                return@execute
            }

            raffleService.clearRaffles(guildId)
            respond(messages.RAFFLES_CLEARED)
        }
    }
}
