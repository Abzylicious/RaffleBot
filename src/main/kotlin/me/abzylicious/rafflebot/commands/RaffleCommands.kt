package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.jda.addReaction
import me.abzylicious.rafflebot.services.RaffleService
import me.jakejmattson.kutils.api.annotations.CommandSet
import me.jakejmattson.kutils.api.arguments.EitherArg
import me.jakejmattson.kutils.api.arguments.GuildEmoteArg
import me.jakejmattson.kutils.api.arguments.MessageArg
import me.jakejmattson.kutils.api.arguments.UnicodeEmoteArg
import me.jakejmattson.kutils.api.dsl.command.commands

@CommandSet("Raffle")
fun raffleCommands(config: BotConfiguration, raffleService: RaffleService, messages: Messages) = commands {
    command("Convert") {
        requiresGuild = true
        description = "Converts a message to a raffle"
        execute(MessageArg, EitherArg(GuildEmoteArg, UnicodeEmoteArg).makeNullableOptional()) {
            val message = it.args.first
            val channel = message.channel
            val reaction = it.args.second?.getData({ emote -> emote.id }, { unicodeEmote -> unicodeEmote }) ?: config.defaultRaffleReaction

            if (!raffleService.addRaffle(channel.id, message.id, reaction)) {
                it.respond(messages.RAFFLE_EXISTS)
                return@execute
            }

            channel.addReaction(message.id, reaction)
            it.respond(messages.MESSAGE_CONVERT_SUCCESS)
        }
    }
}
