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

suspend fun String.toTextChannel() = try { api.getChannelOf<TextChannel>(toSnowflake()) } catch (e: Exception) { null }
suspend fun String.isValidChannelId() = this.toTextChannel() != null

suspend fun String.toGuildEmote(guildId: String) = try { api.guilds.first { it.id.value == guildId }.getEmoji(toSnowflake()) } catch (e: Exception) { null }
suspend fun String.isGuildEmote(guildId: String) = this.toGuildEmote(guildId) != null

fun String.isEmoji() = this.matches(emojiRegex)
suspend fun String.toDisplayableEmote(guildId: String) = if (isGuildEmote(guildId)) toGuildEmote(guildId)!!.mention else this