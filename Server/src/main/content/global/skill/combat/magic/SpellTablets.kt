package content.global.skill.combat.magic

import content.global.skill.combat.magic.spellconsts.Modern
import cfg.consts.Items
import cfg.consts.Sounds
import core.api.playAudio
import core.api.setAttribute
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Spell tablets.
 */
class SpellTablets : InteractionListener {

    // Define the item IDs for Bones to Bananas and Bones to Peaches tablets
    val B2P_TABLET = Items.BONES_TO_PEACHES_8015
    val B2B_TABLET = Items.BONES_TO_BANANAS_8014

    override fun defineListeners() {

        // Listener for breaking Bones to Bananas tablet
        on(B2B_TABLET, IntType.ITEM, "break") { player, node ->
            breakTablet(player) // Call the function to break the tablet
            SpellListeners.run(Modern.BONES_TO_BANANAS, SpellListener.NONE, "modern", player)
            player.inventory.remove(Item(node.id)) // Remove the tablet from the player's inventory
            return@on true
        }

        // Listener for breaking Bones to Peaches tablet
        on(B2P_TABLET, IntType.ITEM, "break") { player, node ->
            breakTablet(player) // Call the function to break the tablet
            SpellListeners.run(Modern.BONES_TO_PEACHES, SpellListener.NONE, "modern", player)
            player.inventory.remove(Item(node.id)) // Remove the tablet from the player's inventory
            return@on true
        }

    }

    /**
     * Break tablet
     *
     * @param player The player who is breaking the tablet
     */
    fun breakTablet(player: Player) {
        playAudio(player, Sounds.POH_TABLET_BREAK_979) // Play the breaking sound
        player.animator.forceAnimation(Animation(4069)) // Force the breaking animation
        player.lock(5) // Lock the player for 5 game ticks
        setAttribute(player, "tablet-spell", true) // Set an attribute for tablet spell
    }
}
