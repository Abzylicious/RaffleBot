package me.abzylicious.rafflebot

import me.jakejmattson.kutils.api.dsl.configuration.startBot
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val token = args.firstOrNull()

    if (token == null) {
        println("Please specify a bot token")
        exitProcess(-1)
    }

    startBot(token)
}
