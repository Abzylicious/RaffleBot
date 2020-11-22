package me.abzylicious.rafflebot.extensions.discordkt

import com.gitlab.kordlib.core.entity.User
import com.gitlab.kordlib.core.entity.channel.MessageChannel
import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.services.LoggingService
import me.jakejmattson.discordkt.api.dsl.CommandEvent
import me.jakejmattson.discordkt.api.dsl.GuildCommandEvent

private val loggingService: LoggingService = LoggingService()

suspend fun CommandEvent<*>.log(configuration: Configuration, guildId: Long, commandNames: List<String>)
        = logAction(configuration, guildId, commandNames, author, channel)

suspend fun GuildCommandEvent<*>.log(configuration: Configuration, guildId: Long, commandNames: List<String>)
        = logAction(configuration, guildId, commandNames, author, channel)

private suspend fun logAction(configuration: Configuration, guildId: Long, commandNames: List<String>, author: User, channel: MessageChannel) {
    val loggingChannel = configuration.guildConfigurations[guildId]?.loggingChannel ?: return
    loggingService.log(loggingChannel, "${author.tag} :: ${author.id.value} invoked ${commandNames.first().toLowerCase()} in ${channel.mention}")
}
