package me.abzylicious.rafflebot.extensions.jda

import me.abzylicious.rafflebot.extensions.stdlib.isEmoji
import me.abzylicious.rafflebot.extensions.stdlib.isGuildEmote
import me.abzylicious.rafflebot.extensions.stdlib.toGuildEmote
import net.dv8tion.jda.api.entities.MessageChannel

fun MessageChannel.addReaction(messageId: String, reaction: String) {
    if (reaction.isGuildEmote())
        this.addReactionById(messageId, reaction.toGuildEmote()!!).queue()

    if (reaction.isEmoji())
        this.addReactionById(messageId, reaction).queue()
}