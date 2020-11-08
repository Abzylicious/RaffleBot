package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.kord.addReaction
import me.abzylicious.rafflebot.services.RaffleService
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.commands

fun raffleCommands(config: BotConfiguration, raffleService: RaffleService, messages: Messages) = commands("Raffle") {
    guildCommand("Convert") {
        description = "Converts a message to a raffle"
        execute(MessageArg, EitherArg(GuildEmojiArg, UnicodeEmojiArg).makeNullableOptional()) {
            val messageId = args.first.id.value
            val channelId = message.getChannel().id.value
            val reaction = args.second?.map({ emote -> emote.id.value }, { unicodeEmote -> unicodeEmote.unicode }) ?: config.defaultRaffleReaction

            if (raffleService.raffleExists(messageId)) {
                respond(messages.RAFFLE_EXISTS)
                return@execute
            }

            raffleService.addRaffle(channelId, messageId, reaction)
            channel.addReaction(messageId, reaction)
            respond(messages.MESSAGE_CONVERT_SUCCESS)
        }
    }
    guildCommand("End"){
        description = "End a given raffle"
        execute(MessageArg, IntegerArg.makeOptional(1))
        {
            val messageId = args.first.id.value
            val winnerCount = args.second

            if (!raffleService.raffleExists(messageId)) {
                respond(messages.RAFFLE_NOT_FOUND)
                return@execute
            }

            val winners = raffleService.resolveRaffle(messageId, winnerCount)
            if (winners.isEmpty()) {
                respond(messages.NO_WINNER_AVAILABLE)
                return@execute
            }

            respond(messages.CONGRATULATION)
            for (winner in winners) {
                respond("${winner.mention} (${winner.name} :: ${winner.id})")
            }

            raffleService.removeRaffle(messageId)
        }
    }
}
