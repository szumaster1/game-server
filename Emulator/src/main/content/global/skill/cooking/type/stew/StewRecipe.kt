package content.global.skill.cooking.type.stew

import content.global.skill.cooking.type.Recipe
import org.rs.consts.Items
import core.api.removeItem
import core.api.sendMessage
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.item.Item

/**
 * Stew recipe.
 */
class StewRecipe : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        val first = event.usedItem
        val second = event.baseItem
        if (first != null && second != null) {
            if (removeItem(player, first) && removeItem(player, second)) {
                if (first.id == BOWL_OF_WATER.id || second.id == BOWL_OF_WATER.id) {
                    player.inventory.add(if (first.id == POTATO.id) INCOMPLETE_STEW else if (first.id == COOKED_MEAT.id) INCOMPLETE_STEW2 else if (second.id == POTATO.id) INCOMPLETE_STEW else if (second.id == COOKED_MEAT.id) INCOMPLETE_STEW2 else null)
                } else {
                    player.inventory.add(UNCOOKED_STEW)
                }
                sendMessage(player, getMixMessage(event))
            }
        }
    }

    override fun getBase(): Item {
        return BOWL_OF_WATER
    }

    override fun getProduct(): Item {
        return UNCOOKED_STEW
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(COOKED_MEAT, POTATO, COOKED_MEAT, POTATO)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(BOWL_OF_WATER, BOWL_OF_WATER, INCOMPLETE_STEW, INCOMPLETE_STEW2)
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You cut up the " + (if (event.usedItem.name.lowercase()
                .contains("incomplete")
        ) event.baseItem.name.lowercase() else event.usedItem.name.lowercase().replace("cooked", "")
            .trim { it <= ' ' }) + " and put it into the stew."
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val UNCOOKED_STEW = Item(Items.UNCOOKED_STEW_2001)
        private val BOWL_OF_WATER = Item(Items.BOWL_OF_WATER_1921)
        private val COOKED_MEAT = Item(Items.COOKED_MEAT_2142)
        private val POTATO = Item(Items.POTATO_1942)
        private val INCOMPLETE_STEW = Item(Items.INCOMPLETE_STEW_1997)
        private val INCOMPLETE_STEW2 = Item(Items.INCOMPLETE_STEW_1999)
    }
}
