package content.global.skill.cooking.recipe.topping.impl

import content.global.skill.cooking.recipe.Recipe
import org.rs.consts.Items
import core.api.getStatLevel
import core.api.rewardXP
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Mushroom and onion.
 */
class MushroomAndOnion : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 57) {
            sendDialogue(player, "You need a Cooking level of at least $57 in order to do this.")
            return
        }
        super.mix(player, event)
        rewardXP(player, Skills.COOKING, 120.0)
    }

    override fun getBase(): Item {
        return FRIED_MUSHROOMS
    }

    override fun getProduct(): Item {
        return MUSHROOM_AND_ONION
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(FRIED_ONIONS)
    }

    override fun getParts(): Array<Item> {
        return emptyArray()
    }

    override fun getMixMessage(event: NodeUsageEvent): String? {
        return null
    }

    override fun isSingular(): Boolean {
        return true
    }

    companion object {
        private val MUSHROOM_AND_ONION = Item(Items.MUSHROOM_AND_ONION_7066)
        private val FRIED_ONIONS = Item(Items.FRIED_ONIONS_7084)
        private val FRIED_MUSHROOMS = Item(Items.FRIED_MUSHROOMS_7082)
    }
}

