package me.abzylicious.rafflebot.extensions.kord

import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.entity.ReactionEmoji
import com.gitlab.kordlib.core.entity.channel.MessageChannel
import me.abzylicious.rafflebot.extensions.stdlib.isEmoji
import me.abzylicious.rafflebot.extensions.stdlib.isGuildEmote
import me.abzylicious.rafflebot.extensions.stdlib.toGuildEmote
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import me.jakejmattson.discordkt.api.extensions.toSnowflake

private lateinit var api: Kord

@Service
class ApiInitializer(discord: Discord) { init { api = discord.api } }

suspend fun MessageChannel.addReaction(guildId: Long, messageId: Long, reaction: String) {
    if (reaction.isGuildEmote(guildId))
        this.getMessage(messageId.toSnowflake()).addReaction(reaction.toGuildEmote(guildId)!!)

    if (reaction.isEmoji())
        this.getMessage(messageId.toSnowflake()).addReaction(ReactionEmoji.Unicode(reaction))
}
