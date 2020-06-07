package me.abzylicious.rafflebot.services

import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.extensions.isValidChannelId
import me.abzylicious.rafflebot.extensions.toChannel
import me.jakejmattson.kutils.api.annotations.Service

@Service
class LoggingService(private val config: BotConfiguration) {
    init {
        if (config.loggingChannel.isValidChannelId())
            log(config.loggingChannel, "Bot successfully initialized!")
    }

    private fun log(logChannelId: String, message: String) {
        val loggingChannel = logChannelId.toChannel() ?: return
        loggingChannel.sendMessage(message).queue()
    }
}