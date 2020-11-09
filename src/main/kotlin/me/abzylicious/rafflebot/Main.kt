package me.abzylicious.rafflebot

import com.gitlab.kordlib.gateway.Intent
import com.google.gson.Gson
import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.services.BotStatsService
import me.abzylicious.rafflebot.services.LoggingService
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.addField
import me.jakejmattson.discordkt.api.extensions.addInlineField
import java.awt.Color

data class Properties(val author: String, val version: String, val discordkt: String, val kotlin: String, val repository: String)

suspend fun main(args: Array<String>) {
    val messages = Messages()
    val token = args.firstOrNull()

    require(token != null) { messages.NO_TOKEN_PROVIDED }

    val propertyFile = Properties::class.java.getResource("/properties.json").readText()
    val project = Gson().fromJson(propertyFile, Properties::class.java)

    bot(token) {
        prefix {
            val configuration = discord.getInjectionObjects(BotConfiguration::class)
            configuration.prefix
        }

        configure {
            theme = Color.CYAN
        }

        intents {
            +Intent.GuildMessages
        }

        mentionEmbed {
            val configuration = it.discord.getInjectionObjects(BotConfiguration::class)
            val botStats = it.discord.getInjectionObjects(BotStatsService::class)
            val self = it.discord.api.getSelf()

            color = it.discord.configuration.theme
            thumbnail { url = self.avatar.url }

            title = self.username
            description = "A multi-guild discord bot to host all the giveaways you could ever want"

            addInlineField("Prefix", configuration.prefix)
            addInlineField("Ping", botStats.ping)

            field {
                name = "Build Info"
                value = "```" +
                        "Version: ${project.version}\n" +
                        "DiscordKt: ${project.discordkt}\n" +
                        "Kotlin: ${project.kotlin}\n" +
                        "```"
            }
            addInlineField("Author", project.author)
            addInlineField("Source", "[GitHub](${project.repository})")
            addField("Uptime", botStats.uptime)
        }

        onStart {
            val logger = this.getInjectionObjects(LoggingService::class)
            val configuration = this.getInjectionObjects(BotConfiguration::class)
            logger.log(configuration.loggingChannel, messages.STARTUP_LOG)
        }
    }
}
