package me.abzylicious.rafflebot.services

import kotlinx.coroutines.runBlocking
import me.abzylicious.rafflebot.configuration.BotConfiguration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.stdlib.isValidChannelId
import me.abzylicious.rafflebot.extensions.stdlib.toTextChannel
import me.jakejmattson.discordkt.api.annotations.Service

@Service
class LoggingService(config: BotConfiguration, messages: Messages) {
    /*init {
        runBlocking {
            if (config.loggingChannel.isValidChannelId())
                log(config.loggingChannel, messages.STARTUP_LOG)

            log(config.loggingChannel, messages.STARTUP_LOG)
        }
    }*/

    suspend fun log(logChannelId: String, message: String) {
        if (!logChannelId.isValidChannelId()) return
        val loggingChannel = logChannelId.toTextChannel()
        loggingChannel!!.createMessage(message)
    }
}