package me.abzylicious.rafflebot.extensions.jda

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel

fun TextChannel.getMessageById(messageId: String): Message = this.retrieveMessageById(messageId).complete()