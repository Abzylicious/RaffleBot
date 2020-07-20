package me.abzylicious.rafflebot.extensions.jda

import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.entities.User

fun MessageReaction.getUsers(): List<User> = this.retrieveUsers().complete()