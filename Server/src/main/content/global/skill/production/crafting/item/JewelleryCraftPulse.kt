package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.JewelleryData
import core.api.*
import core.api.consts.Animations
import core.api.consts.Sounds
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item

/**
 * Represents a pulse for jewellery crafting activities.
 *
 * @property type The type of jewellery item being crafted.
 * @property amount The quantity of the jewellery item to be crafted.
 * @constructor Represents a new instance of the JewelleryCraftPulse class.
 *
 * @param player The player who is crafting the jewellery.
 * @param node The item node associated with the crafting process.
 */
class JewelleryCraftPulse(
    player: Player?, // The player involved in the crafting process, can be null if not applicable.
    node: Item?, // The item node that represents the crafting item, can also be null.
    val type: JewelleryData.JewelleryItem, // The specific type of jewellery item being crafted.
    var amount: Int // The number of items to be crafted, mutable to allow changes.
) : SkillPulse<Item?>(player, node) { // Inherits from SkillPulse, passing player and node parameters.

    companion object {
        private const val ANIMATION = Animations.HUMAN_FURNACE_SMELTING_3243
    }

    var ticks = 0
    override fun checkRequirements(): Boolean {
        return getStatLevel(player, Skills.CRAFTING) >= type.level
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            animate(player, ANIMATION)
            playAudio(player, Sounds.FURNACE_2725)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (player.inventory.remove(*items)) {
            val item = Item(type.sendItem)
            addItem(player, item.id)
            rewardXP(player, Skills.CRAFTING, type.experience)
        }
        amount--
        return amount < 1
    }

    private val items: Array<Item?>
        get() {
            val items = arrayOfNulls<Item>(type.items.size)
            for ((index, i) in type.items.indices.withIndex()) {
                items[index] = Item(type.items[i], 1)
            }
            return items
        }

}
