package me.abzylicious.rafflebot.commands

import me.jakejmattson.kutils.api.annotations.CommandSet
import me.jakejmattson.kutils.api.dsl.command.commands

@CommandSet("Utility")
fun utilityCommands() = commands {
    command("Ping") {
        requiresGuild = true
        description = "Check the status of the bot"
        execute {
            it.respond("Pong! (${it.discord.jda.gatewayPing}ms)")
        }
    }
}
