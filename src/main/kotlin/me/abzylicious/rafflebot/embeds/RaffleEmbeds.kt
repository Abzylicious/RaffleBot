package me.abzylicious.rafflebot.embeds

import com.gitlab.kordlib.rest.builder.message.EmbedBuilder
import me.abzylicious.rafflebot.extensions.stdlib.toDisplayableEmote
import me.abzylicious.rafflebot.persistence.Raffle
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.extensions.addInlineField

suspend fun EmbedBuilder.createRaffleListEmbed(discord: Discord, raffles: List<Raffle>, guildId: Long) {
    color = discord.configuration.theme
    thumbnail { url = discord.api.getSelf().avatar.url }
    title = "Raffles"
    description = if (raffles.isNotEmpty()) { "Currently active raffles" } else { "There are no active raffles currently" }

    if (raffles.isEmpty())
        return

    for (raffle in raffles) {
        addInlineField("Raffle Id (MessageId)", raffle.MessageId.toString())
        addInlineField("Message", "[Jump to](${raffle.MessageUrl})")
        addInlineField("Reaction", raffle.Reaction.toDisplayableEmote(guildId))
    }
}
