package content.global.skill.combat.magic

import content.global.skill.combat.magic.spellconsts.Modern
import org.rs.consts.Items
import org.rs.consts.Sounds
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

        /*
         * Handles conversion of Bones to bananas.
         */

        on(B2B_TABLET, IntType.ITEM, "break") { player, node ->
            breakTablet(player)
            SpellListeners.run(Modern.BONES_TO_BANANAS, SpellListener.NONE, "modern", player)
            player.inventory.remove(Item(node.id))
            return@on true
        }

        /*
         * Handles conversion of Bones to peaches.
         */

        on(B2P_TABLET, IntType.ITEM, "break") { player, node ->
            breakTablet(player)
            SpellListeners.run(Modern.BONES_TO_PEACHES, SpellListener.NONE, "modern", player)
            player.inventory.remove(Item(node.id))
            return@on true
        }

    }

    /**
     * Break the tablet.
     *
     * @param player the player.
     */
    fun breakTablet(player: Player) {
        playAudio(player, Sounds.POH_TABLET_BREAK_979)
        player.animator.forceAnimation(Animation(4069))
        player.lock(5)
        setAttribute(player, "tablet-spell", true)
    }
}
