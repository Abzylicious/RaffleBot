package me.abzylicious.rafflebot.embeds

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.jakejmattson.discordkt.api.Discord

suspend fun EmbedBuilder.createConfigurationMessageEmbed(discord: Discord, title: String, description: String) {
    color = discord.configuration.theme
    thumbnail { url = discord.api.getSelf().avatar.url }
    this.title = title
    this.description = description
}
