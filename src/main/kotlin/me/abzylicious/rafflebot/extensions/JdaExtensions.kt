package me.abzylicious.rafflebot.extensions

import me.jakejmattson.kutils.api.annotations.Service
import me.jakejmattson.kutils.api.Discord
import net.dv8tion.jda.api.JDA

private lateinit var jda: JDA

@Service
class JdaInitializer(discord: Discord) { init { jda = discord.jda } }

fun String.toChannel() = try { jda.getTextChannelById(this) } catch (e: Exception) { null }
fun String.isValidChannelId() = this.toChannel() != null