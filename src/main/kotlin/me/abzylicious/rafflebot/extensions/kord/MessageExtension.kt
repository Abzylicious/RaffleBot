package me.abzylicious.rafflebot.extensions.kord

import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.ReactionEmoji

fun Message.getReaction(reaction: String): ReactionEmoji? {
    val optionalReaction = reactions.parallelStream()
        .filter { it.emoji.name == reaction || it.id?.value == reaction }
        .findFirst()

    return if (optionalReaction.isPresent) optionalReaction.get().emoji else null
}

fun Message.jumpLink(guildId: Long) = "https://discord.com/channels/${guildId}/${channel.id.value}/${id.value}"