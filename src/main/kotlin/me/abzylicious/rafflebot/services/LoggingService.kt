package me.abzylicious.rafflebot.services

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.isValidChannelId
import me.abzylicious.rafflebot.extensions.toChannel
import me.jakejmattson.kutils.api.annotations.Service

@Service
class LoggingService(config: BotConfiguration, messages: Messages) {
    init {
        if (config.loggingChannel.isValidChannelId())
            log(config.loggingChannel, messages.STARTUP_LOG)
    }

    private fun log(logChannelId: String, message: String) {
        if (!logChannelId.isValidChannelId()) return
        val loggingChannel = logChannelId.toChannel()
        loggingChannel!!.sendMessage(message).queue()
    }
}