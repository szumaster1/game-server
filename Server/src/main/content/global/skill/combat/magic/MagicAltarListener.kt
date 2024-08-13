package content.global.skill.combat.magic

import core.api.*
import core.api.consts.Animations
import core.api.consts.Scenery
import core.api.consts.Sounds
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.node.entity.skill.Skills

/**
 * Magic altar listener.
 */
class MagicAltarListener : InteractionListener {

    override fun defineListeners() {
        // Triggered when the player interacts with the ancient or lunar altar.
        on(intArrayOf(ANCIENT_ALTAR, LUNAR_ALTAR), IntType.SCENERY, "pray-at", "pray") { player, node ->
            // Check if the player meets the requirements to swap spell books.
            if (meetsRequirements(player, node)) {
                // Swap the player's spell book based on the altar interacted with.
                swapSpellBook(player, node)
            }

            // Return true to indicate the interaction was handled successfully.
            return@on true
        }
    }

    /**
     * Meets requirements.
     *
     * @param player The player who is interacting with the altar.
     * @param altar  The altar node that the player is interacting with.
     * @return True if the player meets the requirements to interact with the altar, false otherwise.
     */
    private fun meetsRequirements(player: Player, altar: Node): Boolean {
        // Determine the required magic level based on the altar type.
        val level = if (altar.id == ANCIENT_ALTAR) 50 else 65

        // Check if the player has completed the required quest for the altar.
        if (!hasRequirement(player, if (altar.id == ANCIENT_ALTAR) "Desert Treasure" else "Lunar Diplomacy")) {
            return false // Player does not meet quest requirements.
        }

        // Check if the player has the required magic level.
        if (!hasLevelStat(player, Skills.MAGIC, level)) {
            // Inform the player of the required magic level.
            sendMessage(player, "You need a Magic level of at least $level in order to do this.")
            return false // Player does not meet level requirements.
        }

        return true // Player meets all requirements.
    }

    /**
     * Swap spell book.
     *
     * @param player The player who is swapping the spell book.
     * @param altar  The altar node that the player is interacting with.
     */
    private fun swapSpellBook(player: Player, altar: Node) {
        // Lock the player for a short duration to prevent further actions.
        lock(player, 3)
        // Play audio feedback for the player.
        playAudio(player, Sounds.PRAYER_RECHARGE_2674)
        // Trigger the prayer animation for the player.
        animate(player, Animations.HUMAN_PRAY_645)

        // If the player is at the ancient altar, decrement their prayer points.
        if (altar.id == ANCIENT_ALTAR) {
            player.skills.decrementPrayerPoints(player.skills.prayerPoints)
        }

        // Check if the player's current spell book matches the altar type.
        if (SpellBook.forInterface(player.spellBookManager.spellBook) == if (altar.id == ANCIENT_ALTAR) SpellBook.ANCIENT else SpellBook.LUNAR) {
            // Inform the player that they are switching to the modern spell book.
            sendMessage(
                player,
                if (altar.id == ANCIENT_ALTAR) "You feel a strange drain upon your memory..." else "Modern spells activated!"
            )
            // Set the player's spell book to modern.
            player.spellBookManager.setSpellBook(SpellBook.MODERN)
            // Update the player's spell book interface.
            player.spellBookManager.update(player)
        } else {
            // Inform the player that they are switching to the appropriate spell book.
            sendMessage(
                player,
                if (altar.id == ANCIENT_ALTAR) "You feel a strange wisdom fill your mind..." else "Lunar spells activated!"
            )
            // Set the player's spell book to the appropriate type based on the altar.
            player.spellBookManager.setSpellBook(if (altar.id == ANCIENT_ALTAR) SpellBook.ANCIENT else SpellBook.LUNAR)
            // Update the player's spell book interface.
            player.spellBookManager.update(player)
        }
    }

    companion object {
        // Constants representing the IDs of the ancient and lunar altars.
        private const val ANCIENT_ALTAR = Scenery.ALTAR_6552
        private const val LUNAR_ALTAR = Scenery.ALTAR_17010
    }
}
