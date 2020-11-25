package me.abzylicious.rafflebot.extensions.discordkt

import com.gitlab.kordlib.core.entity.GuildEmoji
import com.gitlab.kordlib.kordx.emoji.DiscordEmoji
import me.jakejmattson.discordkt.api.arguments.Either

fun Either<GuildEmoji, DiscordEmoji>.getEmoteIdOrValue() = map({ emote -> emote.id.value }, { unicodeEmote -> unicodeEmote.unicode })
