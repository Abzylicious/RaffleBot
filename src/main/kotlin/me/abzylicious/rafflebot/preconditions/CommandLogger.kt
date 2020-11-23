package me.abzylicious.rafflebot.preconditions

import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.services.LoggingService
import me.jakejmattson.discordkt.api.dsl.precondition

fun commandLogger(loggingService: LoggingService, configuration: Configuration) = precondition {
    command ?: return@precondition fail()

    val guildId = guild?.id?.longValue ?: return@precondition
    val loggingChannelId = configuration.guildConfigurations[guildId]?.loggingChannel ?: return@precondition
    val commandName = command!!.names.first().toLowerCase()
    loggingService.log(loggingChannelId, "${author.tag} :: ${author.id.value} invoked **$commandName** in ${channel.mention}")
    return@precondition
}
