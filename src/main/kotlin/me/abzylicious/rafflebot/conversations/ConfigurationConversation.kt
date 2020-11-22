package me.abzylicious.rafflebot.conversations

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.abzylicious.rafflebot.configuration.Configuration
import me.abzylicious.rafflebot.configuration.Messages
import me.abzylicious.rafflebot.extensions.discordkt.getEmoteIdOrValue
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.conversation

class ConfigurationConversation(private val configuration: Configuration, private val discord: Discord, private val messages: Messages, private val embedBuilder: EmbedBuilder) {
    suspend fun createConfigurationConversation(guildId: Long) = conversation {
        val prefix = promptEmbed(EveryArg.makeOptional(configuration.prefix)) { createMessageEmbed("Setup - Prefix", messages.SETUP_PREFIX) }
        val adminRole = promptEmbed(RoleArg) { createMessageEmbed("Setup - Admin Role", messages.SETUP_ADMIN_ROLE) }
        val staffRole = promptEmbed(RoleArg) { createMessageEmbed("Setup - Staff Role", messages.SETUP_STAFF_ROLE) }
        val loggingChannel = promptEmbed(ChannelArg) { createMessageEmbed("Setup - Logging Channel", messages.SETUP_LOGGING_CHANNEL) }
        val defaultRaffleReactionArg = promptEmbed(EitherArg(GuildEmojiArg, UnicodeEmojiArg).makeNullableOptional()) { createMessageEmbed("Setup - Default Raffle Reaction", messages.SETUP_DEFAULT_RAFFLE_REACTION) }
        val defaultRaffleReaction = defaultRaffleReactionArg?.getEmoteIdOrValue() ?: configuration.defaultRaffleReaction

        configuration.setup(guildId, prefix, adminRole.id.longValue, staffRole.id.longValue, loggingChannel.id.longValue, defaultRaffleReaction)
    }

    private suspend fun createMessageEmbed(title: String, description: String) {
        embedBuilder.color = discord.configuration.theme
        embedBuilder.thumbnail { url = discord.api.getSelf().avatar.defaultUrl }
        embedBuilder.title = title
        embedBuilder.description = description
    }
}
