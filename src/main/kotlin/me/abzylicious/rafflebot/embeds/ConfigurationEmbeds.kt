package me.abzylicious.rafflebot.embeds

import com.gitlab.kordlib.core.entity.Guild
import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.abzylicious.rafflebot.configuration.GuildConfiguration
import me.abzylicious.rafflebot.extensions.stdlib.toDisplayableEmote
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.extensions.addField
import me.jakejmattson.discordkt.api.extensions.toSnowflake

suspend fun EmbedBuilder.createConfigurationMessageEmbed(discord: Discord, title: String, description: String) {
    color = discord.configuration.theme
    thumbnail { url = discord.api.getSelf().avatar.url }
    this.title = title
    this.description = description
}

suspend fun EmbedBuilder.createConfigurationEmbed(discord: Discord, guild: Guild, guildConfiguration: GuildConfiguration) {
    color = discord.configuration.theme
    thumbnail { url = discord.api.getSelf().avatar.url }
    title = "${discord.api.getGuild(guildConfiguration.id.toSnowflake())?.name} - Configuration"
    addField("Bot Prefix", guildConfiguration.prefix)
    addField("Admin Role", guild.getRole(guildConfiguration.adminRole.toSnowflake()).mention)
    addField("Staff Role", guild.getRole(guildConfiguration.staffRole.toSnowflake()).mention)
    addField("Logging Channel", guild.getChannel(guildConfiguration.loggingChannel.toSnowflake()).mention)
    addField("Default Raffle Reaction", guildConfiguration.defaultRaffleReaction.toDisplayableEmote(guildConfiguration.id))
}
