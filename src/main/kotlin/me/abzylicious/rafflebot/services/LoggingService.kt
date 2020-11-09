package me.abzylicious.rafflebot.services

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.stdlib.isValidChannelId
import me.abzylicious.rafflebot.extensions.stdlib.toTextChannel
import me.jakejmattson.discordkt.api.annotations.Service

@Service
class LoggingService(private val config: BotConfiguration, private val messages: Messages) {
    suspend fun log(logChannelId: String, message: String) {
        if (!logChannelId.isValidChannelId()) return
        val loggingChannel = logChannelId.toTextChannel()
        loggingChannel!!.createMessage(message)
    }
}