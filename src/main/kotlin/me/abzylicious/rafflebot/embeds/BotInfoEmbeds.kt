package me.abzylicious.rafflebot.embeds

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.abzylicious.rafflebot.services.BotStatsService
import me.jakejmattson.discordkt.api.dsl.DiscordContext
import me.jakejmattson.discordkt.api.extensions.addInlineField

data class Project(val author: String, val version: String, val discordkt: String, val kotlin: String, val repository: String)

suspend fun EmbedBuilder.createBotInformationEmbed(discordContext: DiscordContext, project: Project) {
    val botStatsService = discordContext.discord.getInjectionObjects(BotStatsService::class)
    val self = discordContext.discord.api.getSelf()

    color = discordContext.discord.configuration.theme
    thumbnail { url = self.avatar.url }

    title = self.tag
    description = "A multi-guild discord bot to host all the giveaways you could ever want"

    addInlineField("Author", project.author)
    addInlineField("Source", "[GitHub](${project.repository})")
    addInlineField("Prefix", discordContext.prefix())

    field {
        name = "Build Info"
        value = "```" +
                "Version: ${project.version}\n" +
                "DiscordKt: ${project.discordkt}\n" +
                "Kotlin: ${project.kotlin}\n" +
                "```"
    }

    addInlineField("Uptime", botStatsService.uptime)
    addInlineField("Ping", botStatsService.ping)
}
