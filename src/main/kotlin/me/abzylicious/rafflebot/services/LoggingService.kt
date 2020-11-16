package me.abzylicious.rafflebot.services

import me.abzylicious.rafflebot.extensions.stdlib.isValidChannelId
import me.abzylicious.rafflebot.extensions.stdlib.toTextChannel
import me.jakejmattson.discordkt.api.annotations.Service

@Service
class LoggingService {
    suspend fun log(logChannelId: String, message: String) {
        if (!logChannelId.isValidChannelId()) return
        val loggingChannel = logChannelId.toTextChannel()
        loggingChannel!!.createMessage(message)
    }
}