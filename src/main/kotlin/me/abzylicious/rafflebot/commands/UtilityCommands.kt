package me.abzylicious.rafflebot.commands

import me.jakejmattson.discordkt.api.dsl.commands
import kotlin.time.ExperimentalTime

fun utilityCommands() = commands("Utility") {
    guildCommand("Ping") {
        description = "Check the status of the bot"
        execute {
            respond("Pong! (${discord.api.gateway.averagePing})")
        }
    }
}
