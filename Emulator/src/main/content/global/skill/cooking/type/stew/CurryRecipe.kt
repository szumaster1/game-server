package content.global.skill.cooking.type.stew

import content.global.skill.cooking.type.Recipe
import org.rs.consts.Items
import core.api.removeItem
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Curry recipe.
 */
class CurryRecipe : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (event.baseItem.id == CURRY_LEAF.id || event.usedItem.id == CURRY_LEAF.id) {
            val stew = if (event.baseItem.id == UNCOOKED_STEW.id) event.baseItem else event.usedItem
            if (stew.charge == 1000) {
                stew.charge = 1
            }
            val charge = stew.charge
            if (charge < 3) {
                player.inventory.remove(CURRY_LEAF)
                stew.charge = charge + 1
            } else {
                if (removeItem(player, stew) && removeItem(player, CURRY_LEAF)) {
                    player.inventory.add(UNCOOKED_CURRY)
                }
            }
            return
        }
        if (removeItem(player, event.baseItem) && removeItem(player, event.usedItem)) {
            player.inventory.add(product)
        }
    }

    override fun getBase(): Item {
        return UNCOOKED_STEW
    }

    override fun getProduct(): Item {
        return UNCOOKED_CURRY
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(SPICE, CURRY_LEAF)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(UNCOOKED_STEW)
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You mix the spice with the stew."
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val UNCOOKED_CURRY = Item(Items.UNCOOKED_CURRY_2009)
        private val UNCOOKED_STEW = Item(Items.UNCOOKED_STEW_2001)
        private val SPICE = Item(Items.SPICE_2007)
        private val CURRY_LEAF = Item(Items.CURRY_LEAF_5970)
    }
}
