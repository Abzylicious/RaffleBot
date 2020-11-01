package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.kord.addReaction
import me.abzylicious.rafflebot.services.RaffleService
import me.jakejmattson.discordkt.api.arguments.EitherArg
import me.jakejmattson.discordkt.api.arguments.GuildEmojiArg
import me.jakejmattson.discordkt.api.arguments.MessageArg
import me.jakejmattson.discordkt.api.arguments.UnicodeEmojiArg
import me.jakejmattson.discordkt.api.dsl.commands

fun raffleCommands(config: BotConfiguration, raffleService: RaffleService, messages: Messages) = commands("Raffle") {
    guildCommand("Convert") {
        description = "Converts a message to a raffle"
        execute(MessageArg, EitherArg(GuildEmojiArg, UnicodeEmojiArg).makeNullableOptional()) {
            val message = args.first
            val channel = message.getChannel()
            val reaction = args.second?.map({ emote -> emote.id.value }, { unicodeEmote -> unicodeEmote.unicode }) ?: config.defaultRaffleReaction

            if (!raffleService.addRaffle(channel.id.value, message.id.value, reaction)) {
                respond(messages.RAFFLE_EXISTS)
                return@execute
            }

            channel.addReaction(message.id.value, reaction)
            respond(messages.MESSAGE_CONVERT_SUCCESS)
        }
    }
}
