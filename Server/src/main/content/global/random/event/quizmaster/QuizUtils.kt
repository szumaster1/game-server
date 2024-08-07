package content.global.random.event.quizmaster

import core.api.consts.Items
import core.api.getAttribute
import core.api.removeAttributes
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.map.Location

/**
 * Quiz utils.
 */
object QuizUtils {
    // Attributes
    const val PLAYER_LOCATION = "/save:original-loc"

    // Items
    val COINS = Item(Items.COINS_995, 1000)
    val MYSTERY_BOX = Item(Items.MYSTERY_BOX_6199)

    // Locations
    val EVENT_LOCATION: Location = Location.create(1952, 4764, 1)

    // Correct dialogues
    val CORRECT = arrayOf(
        arrayOf("Hey, you're good at this!", "CORRECT!", "Okay, next question!"),
        arrayOf("Absolutely RIGHT!", "Keep going for the win!", "Okay, next question!"),
        arrayOf("Wow, you're a smart one!", "You're absolutely right!", "Okay, next question!"),
        arrayOf("COR-RECT!", "Okay, next question!"),
        arrayOf("DING DING DING", "That's RIGHT! Good for you!", "Okay, next question!"),
        arrayOf("YES!", "You're RIGHT!", "Okay, next question!")
    )

    // Wrong dialogues
    val WRONG = arrayOf(
        arrayOf(
            "Huh...? Didn't you know that one?",
            "You're supposed to pick the ODD ONE OUT.",
            "Now, let's start again..."
        ),
        arrayOf(
            "No. No, that's not right at all.",
            "You're supposed to pick the ODD ONE OUT.",
            "Now let's start again..."
        ),
        arrayOf("No, sorry, Try harder!", "You're supposed to pick the ODD ONE OUT.", "Now, let's start again..."),
        arrayOf("Better luck next time!", "You're supposed to pick the ODD ONE OUT.", "Now, let's start again..."),
        arrayOf("WRONG! That's just WRONG!", "You're supposed to pick the ODD ONE OUT.", "Now, let's start again..."),
        arrayOf("WRONG WRONG WRONG!", "You're supposed to pick the ODD ONE OUT.", "Now, let's start again..."),
        arrayOf(
            "No, no, no... That's completely WRONG!",
            "You're supposed to pick the ODD ONE OUT.",
            "Now, let's start again..."
        ),
        arrayOf("Nope, that's not it.", "You're supposed to pick the ODD ONE OUT.", "Now, let's start again..."),
        arrayOf("BZZZZZZZ! WRONG!", "You're supposed to pick the ODD ONE OUT.", "Now, let's start again...")
    )

    /**
     * Cleanup.
     *
     * @param player The player to clean up.
     */
    fun cleanup(player: Player) {
        player.locks.unlockTeleport()
        player.properties.teleportLocation = getAttribute(player, PLAYER_LOCATION, null)
        removeAttributes(player, PLAYER_LOCATION)
    }

}
