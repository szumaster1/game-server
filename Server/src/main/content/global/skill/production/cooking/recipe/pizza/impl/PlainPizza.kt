package content.global.skill.production.cooking.recipe.pizza.impl

import content.global.skill.production.cooking.recipe.Recipe
import core.api.consts.Items
import core.api.getStatLevel
import core.api.sendDialogue
import core.game.interaction.NodeUsageEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

class PlainPizza : Recipe() {

    override fun mix(player: Player, event: NodeUsageEvent) {
        if (getStatLevel(player, Skills.COOKING) < 35) {
            sendDialogue(player, "You need a Cooking level of at least " + 35 + " in order to do this.")
            return
        }
        super.mix(player, event)
    }

    override fun getBase(): Item {
        return PIZZA_BASE
    }

    override fun getProduct(): Item {
        return UNCOOKED_PIZZA
    }

    override fun getIngredients(): Array<Item> {
        return arrayOf(TOMATO, CHEESE)
    }

    override fun getParts(): Array<Item> {
        return arrayOf(PIZZA_BASE, INCOMPLETE_PIZZA, UNCOOKED_PIZZA)
    }

    override fun getMixMessage(event: NodeUsageEvent): String {
        return "You add the " + event.baseItem.name.lowercase() + " to the pizza."
    }

    override fun isSingular(): Boolean {
        return false
    }

    companion object {
        private val PIZZA_BASE = Item(Items.PIZZA_BASE_2283)
        private val UNCOOKED_PIZZA = Item(Items.UNCOOKED_PIZZA_2287)
        private val INCOMPLETE_PIZZA = Item(Items.INCOMPLETE_PIZZA_2285)
        private val TOMATO = Item(Items.TOMATO_1982)
        private val CHEESE = Item(Items.CHEESE_1985)
    }
}
