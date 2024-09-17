package content.global.skill.production.cooking.recipe.topping.impl

import content.global.skill.production.cooking.recipe.Recipe
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Spicy sauce.
 */
class SpicySauce : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 9) {
            sendDialogue(player, "You need a Cooking level of at least $9 in order to do this.")
            return
        }
        super.mix(player, event)
        if (event.baseItem.id == GNOME_SPICE.id || event.usedItem.id == GNOME_SPICE.id) {
            rewardXP(player, Skills.COOKING, 25.0)
        }
    }

    override fun getBase(): Item {
        return BOWL
    }

    override fun getProduct(): Item {
        return SPICY_SAUCE
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(GARLIC, GNOME_SPICE)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(BOWL, CHOPPED_GARLIC, SPICY_SAUCE)
    }

    override fun getMixMessage(event: NodeUsageEvent): String? {
        return null
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val SPICY_SAUCE = Item(Items.SPICY_SAUCE_7072)
        private val BOWL = Item(Items.BOWL_1923)
        private val GARLIC = Item(Items.GARLIC_1550)
        private val CHOPPED_GARLIC = Item(Items.CHOPPED_GARLIC_7074)
        private val GNOME_SPICE = Item(Items.GNOME_SPICE_2169)
    }
}

