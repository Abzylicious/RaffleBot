package me.abzylicious.rafflebot.configuration

import me.jakejmattson.discordkt.api.dsl.Data

data class Messages(
    val NO_TOKEN_PROVIDED: String = "Expected the bot token as argument or an environment variable",
    val GUILD_CONFIGURATION_EXISTS: String = "Guild configuration exists. To modify it use the respective commands to set values.",
    val GUILD_CONFIGURATION_NOT_FOUND: String = "Guild configuration not found. Please run the **configure** command to set this initially.",
    val SETUP_PREFIX_DECISION: String = "Would you like to set a new bot prefix? The default bot prefix is **raffle!**. This can be changed later via the **setprefix** command.",
    val SETUP_PREFIX: String = "Set the bot prefix for this guild.",
    val SETUP_ADMIN_ROLE: String = "Set the bot admin role for this guild. This can be changed later via the **setadminrole** command.",
    val SETUP_STAFF_ROLE: String = "Set the bot staff role for this guild. This can be changed later via the **setstaffrole** command.",
    val SETUP_LOGGING_CHANNEL: String = "Set the bot logging channel. This can be changed later via the **setloggingchannel** command.",
    val SETUP_DEFAULT_RAFFLE_REACTION_DECISION: String = "Would you like to set a default reaction for your raffles? The default reaction is \uD83C\uDF89. This can be changed later via the **setdefaultreaction** command.",
    val SETUP_DEFAULT_RAFFLE_REACTION: String = "Set the default reaction for raffles of this guild.",
    val SETUP_COMPLETE: String = "setup complete! \uD83C\uDF89",
    val STARTUP_LOG: String = "Bot successfully initialized!",
    val MESSAGE_CONVERT_SUCCESS: String = "Message successfully converted and under my watch now",
    val RAFFLE_EXISTS: String = "This raffle already exists",
    val RAFFLE_NOT_FOUND: String = "Raffle not found",
    val RAFFLE_REMOVED: String = "Raffle successfully removed",
    val RAFFLES_CLEARED: String = "Successfully removed all raffles",
    val NO_RAFFLES_AVAILABLE: String = "There are no raffles",
    val NO_WINNER_AVAILABLE: String = "No one participated in this raffle or you tried to draw more winners than there are participants",
    val CONGRATULATION: String = "Congratulations to the following winner(s)!"
) : Data("config/messages.json", false)
