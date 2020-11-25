package me.abzylicious.rafflebot.extensions.stdlib

import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.entity.channel.TextChannel
import kotlinx.coroutines.flow.first
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import me.jakejmattson.discordkt.api.extensions.toSnowflake

private lateinit var api: Kord
private val emojiRegex = "[^\\x00-\\x7F]+ *(?:[^\\x00-\\x7F]| )*".toRegex()

@Service
class ApiInitializer(discord: Discord) { init { api = discord.api } }

suspend fun Long.toTextChannel() = try { api.getChannelOf<TextChannel>(toSnowflake()) } catch (e: Exception) { null }
suspend fun Long.isValidChannelId() = this.toTextChannel() != null

suspend fun String.toGuildEmote(guildId: Long) = try { api.guilds.first { it.id.longValue == guildId }.getEmoji(toSnowflake()) } catch (e: Exception) { null }
suspend fun String.isGuildEmote(guildId: Long) = this.toGuildEmote(guildId) != null

fun String.isEmoji() = this.matches(emojiRegex)
suspend fun String.toDisplayableEmote(guildId: Long) = if (isGuildEmote(guildId)) toGuildEmote(guildId)!!.mention else this