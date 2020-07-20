package me.abzylicious.rafflebot.extensions.jda

import net.dv8tion.jda.api.entities.Message

fun Message.getReaction(reaction: String) = this.reactions.stream()
    .filter { it.reactionEmote.name == reaction || it.reactionEmote.id == reaction }
    .findFirst().get()