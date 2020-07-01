package me.abzylicious.rafflebot.extensions.stdlib

import me.jakejmattson.kutils.api.Discord
import me.jakejmattson.kutils.api.annotations.Service
import net.dv8tion.jda.api.JDA

private lateinit var jda: JDA
private val emojiRegex = "[^\\x00-\\x7F]+ *(?:[^\\x00-\\x7F]| )*".toRegex()

@Service
class JdaInitializer(discord: Discord) { init { jda = discord.jda } }

fun String.toChannel() = try { jda.getTextChannelById(this) } catch (e: Exception) { null }
fun String.isValidChannelId() = this.toChannel() != null

fun String.toGuildEmote() = try { jda.getEmoteById(this) } catch (e: Exception) { null }
fun String.isGuildEmote() = this.toGuildEmote() != null

fun String.isEmoji() = this.matches(emojiRegex)