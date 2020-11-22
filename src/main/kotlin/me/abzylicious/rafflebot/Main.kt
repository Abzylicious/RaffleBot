package me.abzylicious.rafflebot

import com.gitlab.kordlib.gateway.Intent
import com.google.gson.Gson
import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.embeds.Project
import me.abzylicious.rafflebot.embeds.createBotInformationEmbed
import me.abzylicious.rafflebot.services.LoggingService
import me.jakejmattson.discordkt.api.dsl.bot
import java.awt.Color

suspend fun main(args: Array<String>) {
    val messages = Messages()
    val token = args.firstOrNull()

    require(token != null) { messages.NO_TOKEN_PROVIDED }

    val propertyFile = Project::class.java.getResource("/properties.json").readText()
    val project = Gson().fromJson(propertyFile, Project::class.java)

    bot(token) {
        prefix {
            val configuration = discord.getInjectionObjects(Configuration::class)
            guild?.let { configuration[it.id.longValue]?.prefix } ?: configuration.prefix
        }

        configure {
            theme = Color.CYAN
        }

        intents {
            +Intent.GuildMessages
        }

        mentionEmbed {
            createBotInformationEmbed(it, project)
        }

        onStart {
            val logger = this.getInjectionObjects(LoggingService::class)
            val configuration = this.getInjectionObjects(Configuration::class)
            configuration.guildConfigurations.forEach { logger.log(it.value.loggingChannel, messages.STARTUP_LOG) }
        }
    }
}
