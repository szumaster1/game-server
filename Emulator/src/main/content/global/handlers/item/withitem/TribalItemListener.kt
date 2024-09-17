package content.global.handlers.item.withitem

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import org.rs.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Handles use the hammer on tribal mask.
 */
class TribalItemListener : InteractionListener {
    val HAMMER = Items.HAMMER_2347
    val TRIBAL_MASK_GREEN = Items.TRIBAL_MASK_6335
    val TRIBAL_MASK_YELLOW = Items.TRIBAL_MASK_6337
    val TRIBAL_MASK_WHITE = Items.TRIBAL_MASK_6339
    val BROODOO_SHIELD_GREEN = Items.BROODOO_SHIELD_10_6215
    val BROODOO_SHIELD_YELLOW = Items.BROODOO_SHIELD_10_6237
    val BROODOO_SHIELD_WHITE = Items.BROODOO_SHIELD_10_6259

    val NAILS = arrayOf(Items.BRONZE_NAILS_4819, Items.IRON_NAILS_4820, Items.STEEL_NAILS_1539, Items.BLACK_NAILS_4821, Items.MITHRIL_NAILS_4822, Items.ADAMANTITE_NAILS_4823, Items.RUNE_NAILS_4824)
    val SNAKE_SKIN = Items.SNAKESKIN_6289

    override fun defineListeners() {
        onUseWith(IntType.ITEM, HAMMER, TRIBAL_MASK_GREEN) { player, _, _ ->
            animate(player, 2410)
            return@onUseWith makeShield(player, BROODOO_SHIELD_GREEN, TRIBAL_MASK_GREEN)
        }

        onUseWith(IntType.ITEM, HAMMER, TRIBAL_MASK_YELLOW) { player, _, _ ->
            animate(player, 2411)
            return@onUseWith makeShield(player, BROODOO_SHIELD_YELLOW, TRIBAL_MASK_YELLOW)
        }

        onUseWith(IntType.ITEM, HAMMER, TRIBAL_MASK_WHITE) { player, _, _ ->
            animate(player, 2409)
            return@onUseWith makeShield(player, BROODOO_SHIELD_WHITE, TRIBAL_MASK_WHITE)
        }
    }

    private fun makeShield(player: Player, shieldId: Int, maskId: Int): Boolean {
        if (player.getSkills().getStaticLevel(Skills.CRAFTING) >= 35) {
            for (nail in NAILS) {
                val resourcesNeeded = arrayOf(Item(nail, 8), Item(SNAKE_SKIN, 2), Item(maskId, 1))

                if (player.inventory.containsItems(*resourcesNeeded)) {
                    if (player.inventory.remove(*resourcesNeeded)) {
                        addItemOrDrop(player, shieldId, 1)
                        sendMessage(player, "You create Broodoo a shield!")
                        player.getSkills().addExperience(Skills.CRAFTING, 100.0, true)
                        return true
                    }
                }
            }

            // if none of the combinations worked, return false here
            sendMessage(player, "You don't have the resources needed to create a Broodoo Shield.")
            return false
        }
        // if players lack the required crafting level
        sendMessage(player, "You don't have the crafting level needed to do that.")
        return false
    }

}