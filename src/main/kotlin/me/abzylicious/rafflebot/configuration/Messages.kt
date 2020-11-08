package me.abzylicious.rafflebot.configuration

import me.jakejmattson.discordkt.api.dsl.Data

data class Messages(
    val NO_TOKEN_PROVIDED: String = "Please specify a bot token",
    val STARTUP_LOG: String = "Bot successfully initialized!",
    val RAFFLE_EXISTS: String = "This raffle already exists",
    val NO_RAFFLES_AVAILABLE: String = "There are no raffles",
    val RAFFLE_NOT_FOUND: String = "Raffle not found",
    val RAFFLE_REMOVED: String = "Raffle successfully removed",
    val RAFFLES_CLEARED: String = "Successfully removed all raffles",
    val NO_WINNER_AVAILABLE: String = "No one participated in this raffle or you tried to draw more winners than there are participants",
    val MESSAGE_CONVERT_SUCCESS: String = "Message successfully converted and under my watch now",
    val CONGRATULATION: String = "Congratulations to the following winner(s)!"
) : Data("config/messages.json")