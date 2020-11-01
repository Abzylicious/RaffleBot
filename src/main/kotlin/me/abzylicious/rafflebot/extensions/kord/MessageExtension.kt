package me.abzylicious.rafflebot.extensions.kord

import com.gitlab.kordlib.core.entity.Message

fun Message.getReaction(reaction: String) = this.reactions.parallelStream()
    .filter { it.emoji.name == reaction || it.id?.value == reaction }
    .findFirst().get().emoji