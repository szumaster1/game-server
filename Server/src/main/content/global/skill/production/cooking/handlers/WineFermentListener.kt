package content.global.skill.production.cooking.handlers

import content.global.skill.production.cooking.fermenting.WineFermentingPulse
import core.api.*
import core.api.consts.Items
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills

class WineFermentListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.JUG_OF_WATER_1937, Items.GRAPES_1987) { player, used, with ->
            if (getStatLevel(player, Skills.COOKING) < 35) {
                sendDialogue(player, "You need a cooking level of 35 to do this.")
                return@onUseWith false
            }
            if (removeItem(player, used.asItem()) && removeItem(player, with.asItem())) {
                addItem(player, Items.UNFERMENTED_WINE_1995)
                submitIndividualPulse(player, WineFermentingPulse(1, player))
            }

            return@onUseWith true
        }
    }
}
