package me.abzylicious.rafflebot.extensions.kord

import com.gitlab.kordlib.core.entity.Message
import com.gitlab.kordlib.core.entity.channel.TextChannel

fun TextChannel.getMessageById(messageId: String): Message = this.getMessageById(messageId)