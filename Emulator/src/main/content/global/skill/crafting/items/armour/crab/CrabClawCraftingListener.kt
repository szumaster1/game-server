package content.global.skill.crafting.items.armour.crab

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import org.rs.consts.Animations
import org.rs.consts.Items

class CrabClawCraftingListener : InteractionListener {

    override fun defineListeners() {
        onUseWith(IntType.ITEM, Items.CHISEL_1755, Items.FRESH_CRAB_SHELL_7538) { player, _, with ->
            if (getStatLevel(player, Skills.CRAFTING) < 15) {
                sendDialogue(player, "You need a crafting level of at least 15 in order to do this.")
                return@onUseWith false
            }
            // Recipe for Disaster quest.
            animate(player, Animations.CRAFT_SOMETHING_1309)
            if (removeItem(player, with.asItem())) {
                addItem(player, Items.CRAB_HELMET_7539)
                rewardXP(player, Skills.CRAFTING, 32.5)
            }
            return@onUseWith true
        }
    }
}