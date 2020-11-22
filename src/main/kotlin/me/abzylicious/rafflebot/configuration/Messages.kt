package me.abzylicious.rafflebot.configuration

import me.jakejmattson.discordkt.api.dsl.Data

data class Messages(
    val NO_TOKEN_PROVIDED: String = "Please specify a bot token",
    val SETUP_PREFIX: String = "Set the bot prefix for this guild (**default** raffle!). This can be changed later via the **setprefix** command.",
    val SETUP_ADMIN_ROLE: String = "Set the bot admin role for this guild. This can be changed later via the **setadminrole** command.",
    val SETUP_STAFF_ROLE: String = "Set the bot staff role for this guild. This can be changed later via the **setstaffrole** command.",
    val SETUP_LOGGING_CHANNEL: String = "Set the bot logging channel. This can be changed later via the **setloggingchannel** command.",
    val SETUP_DEFAULT_RAFFLE_REACTION: String = "Set the default reaction for raffles of this guild (**default** \uD83C\uDF89). This can be changed later via the **setdefaultrafflereaction** command.",
    val STARTUP_LOG: String = "Bot successfully initialized!",
    val MESSAGE_CONVERT_SUCCESS: String = "Message successfully converted and under my watch now",
    val RAFFLE_EXISTS: String = "This raffle already exists",
    val RAFFLE_NOT_FOUND: String = "Raffle not found",
    val RAFFLE_REMOVED: String = "Raffle successfully removed",
    val RAFFLES_CLEARED: String = "Successfully removed all raffles",
    val NO_RAFFLES_AVAILABLE: String = "There are no raffles",
    val NO_WINNER_AVAILABLE: String = "No one participated in this raffle or you tried to draw more winners than there are participants",
    val CONGRATULATION: String = "Congratulations to the following winner(s)!"
) : Data("config/messages.json")