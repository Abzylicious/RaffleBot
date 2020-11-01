package me.abzylicious.rafflebot.extensions.kord

import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.ReactionEmoji

fun Message.getReaction(reaction: String) = this.reactions.stream()
    .filter { it.emoji.name == reaction || it.id?.value == reaction }
    .findFirst().get() as ReactionEmoji