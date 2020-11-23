package me.abzylicious.rafflebot.commands

import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.extensions.discordkt.log
import me.jakejmattson.discordkt.api.dsl.commands

fun utilityCommands(configuration: Configuration) = commands("Utility") {
    guildCommand("Ping") {
        description = "Check the status of the bot"
        execute {
            respond("Pong! (${discord.api.gateway.averagePing})")
            log(configuration, guild.id.longValue, names)
        }
    }
}
