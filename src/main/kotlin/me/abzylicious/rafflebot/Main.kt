package me.abzylicious.rafflebot

import com.gitlab.kordlib.gateway.Intent
import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.services.LoggingService
import me.jakejmattson.discordkt.api.dsl.bot

suspend fun main(args: Array<String>) {
    val messages = Messages()
    val token = args.firstOrNull()

    require(token != null) { messages.NO_TOKEN_PROVIDED }

    bot(token) {
        prefix {
            val configuration = discord.getInjectionObjects(BotConfiguration::class)
            configuration.prefix
        }

        intents {
            +Intent.GuildMessages
        }

        onStart {
            val logger = this.getInjectionObjects(LoggingService::class)
            val configuration = this.getInjectionObjects(BotConfiguration::class)
            logger.log(configuration.loggingChannel, messages.STARTUP_LOG)
        }
    }
}
