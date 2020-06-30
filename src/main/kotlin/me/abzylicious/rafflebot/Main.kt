package me.abzylicious.rafflebot

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.jakejmattson.kutils.api.dsl.configuration.startBot
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val messages = Messages()
    val token = args.firstOrNull()

    if (token == null) {
        println(messages.NO_TOKEN_PROVIDED)
        exitProcess(-1)
    }

    startBot(token) {
        val configuration: BotConfiguration = discord.getInjectionObjects(BotConfiguration::class)
        configure {
            prefix { configuration.prefix }
        }
    }
}
