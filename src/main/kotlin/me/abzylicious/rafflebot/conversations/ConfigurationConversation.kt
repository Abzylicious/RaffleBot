package me.abzylicious.rafflebot.conversations

import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.embeds.createConfigurationMessageEmbed
import me.abzylicious.rafflebot.extensions.discordkt.getEmoteIdOrValue
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.conversation

class ConfigurationConversation(private val configuration: Configuration, private val messages: Messages) {
    suspend fun createConfigurationConversation(guildId: Long) = conversation {
        val prefix = promptEmbed(EveryArg) {
            createConfigurationMessageEmbed(discord, "Setup - Prefix", messages.SETUP_PREFIX)
        }

        val adminRole = promptEmbed(RoleArg) {
            createConfigurationMessageEmbed(discord, "Setup - Admin Role", messages.SETUP_ADMIN_ROLE)
        }

        val staffRole = promptEmbed(RoleArg) {
            createConfigurationMessageEmbed(discord, "Setup - Staff Role", messages.SETUP_STAFF_ROLE)
        }

        val loggingChannel = promptEmbed(ChannelArg) {
            createConfigurationMessageEmbed(discord, "Setup - Logging Channel", messages.SETUP_LOGGING_CHANNEL)
        }

        val defaultRaffleReactionArg = promptEmbed(EitherArg(GuildEmojiArg, UnicodeEmojiArg)) {
            createConfigurationMessageEmbed(discord, "Setup - Default Raffle Reaction", messages.SETUP_DEFAULT_RAFFLE_REACTION)
        }

        val defaultRaffleReaction = defaultRaffleReactionArg.getEmoteIdOrValue()
        configuration.setup(guildId, prefix, adminRole.id.longValue, staffRole.id.longValue, loggingChannel.id.longValue, defaultRaffleReaction)
    }
}
