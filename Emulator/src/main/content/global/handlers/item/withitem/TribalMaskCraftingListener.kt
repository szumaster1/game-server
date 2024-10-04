package content.global.handlers.item.withitem

import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import org.rs.consts.Animations
import org.rs.consts.Items

/**
 * Handles use the hammer on tribal mask.
 */
class TribalMaskCraftingListener : InteractionListener {

    private val itemIDs = mapOf(Items.TRIBAL_MASK_6335 to Items.BROODOO_SHIELD_10_6215, Items.TRIBAL_MASK_6337 to Items.BROODOO_SHIELD_10_6237, Items.TRIBAL_MASK_6339 to Items.BROODOO_SHIELD_10_6259)
    private val nailIDs = arrayOf(Items.BRONZE_NAILS_4819, Items.IRON_NAILS_4820, Items.STEEL_NAILS_1539, Items.BLACK_NAILS_4821, Items.MITHRIL_NAILS_4822, Items.ADAMANTITE_NAILS_4823, Items.RUNE_NAILS_4824)
    private val snakeskinId = Items.SNAKESKIN_6289

    override fun defineListeners() {
        itemIDs.forEach { (maskId, shieldId) ->
            onUseWith(IntType.ITEM, Items.HAMMER_2347, maskId) { player, _, _ ->
                val animation = when (maskId) {
                    Items.TRIBAL_MASK_6335 -> Animations.CRAFT_BROODOO_SHIELD_GREEN_2410
                    Items.TRIBAL_MASK_6337 -> Animations.CRAFT_BROODOO_SHIELD_ORANGE_2411
                    Items.TRIBAL_MASK_6339 -> Animations.CRAFT_BROODOO_SHIELD_WHITE_2409
                    else -> return@onUseWith false
                }
                animate(player, animation)
                return@onUseWith makeShield(player, shieldId, maskId)
            }
        }
    }

    private fun makeShield(player: Player, shieldId: Int, maskId: Int): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < 35) {
            sendMessage(player, "You don't have the crafting level needed to do that.")
            return false
        }

        val resourcesNeeded = arrayOf(Item(snakeskinId, 2), Item(maskId, 1))
        for (nail in nailIDs) {
            if (!player.inventory.containsItems(Item(nail, 8), *resourcesNeeded)) {
                sendMessage(player, "You don't have the resources needed to create a Broodoo Shield.")
                return false
            }
        }

        if (!removeItem(player, resourcesNeeded + Array(8) { Item(nailIDs[0], 1) })) {
            sendMessage(player, "You don't have the resources needed to create a Broodoo Shield.")
            return false
        }

        addItemOrDrop(player, shieldId, 1)
        sendMessage(player, "You create a Broodoo shield!")
        rewardXP(player, Skills.CRAFTING, 100.0)
        return true
    }

}