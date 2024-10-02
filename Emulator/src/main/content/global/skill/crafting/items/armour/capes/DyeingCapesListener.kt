package content.global.skill.crafting.items.armour.capes

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import org.rs.consts.Items

private val DYES = Dye.values().map { it.item.id }.toIntArray()
private val CAPE = Cape.values().map { it.cape.id }.toIntArray()

/**
 * Handles the dyeing of a cape.
 */
class DyeingCapesListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, DYES, *CAPE) { player, used, with ->
            val product = Cape.forDye(used.id)!!

            if (!inInventory(player, used.id)) {
                sendMessage(player, "You don't have the required item to make this.")
                return@onUseWith false
            }

            if (removeItem(player, used.id)) {
                replaceSlot(player, with.index, product.cape)
                addItem(player, Items.VIAL_229)
                rewardXP(player, Skills.CRAFTING, 2.0)
                sendMessage(player, "You dye the cape.")
            }
            return@onUseWith true
        }
    }
}