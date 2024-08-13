package content.global.skill.production.smithing.item

import content.global.skill.production.smithing.data.Bars
import content.global.skill.production.smithing.data.SmithingType
import content.global.skill.skillcape.SkillcapePerks
import content.global.skill.skillcape.SkillcapePerks.Companion.isActive
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.cache.def.impl.ItemDefinition
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils

/**
 * Smithing pulse
 *
 * @property bars Represents the type of bars used in smithing.
 * @property amount Indicates the quantity of items to be crafted.
 * @constructor Represents a new instance of SmithingPulse.
 *
 * @param player The player who is performing the smithing action.
 * @param item The item that is being crafted.
 */
class SmithingPulse(player: Player?, item: Item?, private val bars: Bars, private var amount: Int) :
    SkillPulse<Item?>(player, item) {

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, bars.barType.bar, bars.smithingType.requiredBar * amount)) {
            amount = amountInInventory(player, bars.barType.bar)
        }
        closeInterface(player)
        if (getStatLevel(player, Skills.SMITHING) < bars.level) {
            sendDialogue(player, "You need a Smithing level of " + bars.level + " to make a " + getItemName(bars.product) + ".")
            return false
        }
        if (!inInventory(player, bars.barType.bar, bars.smithingType.requiredBar)) {
            sendDialogue(player, "You don't have enough " + getItemName(bars.barType.bar).lowercase() + "s to make a " + bars.smithingType.name.replace("TYPE_", "").replace("_", " ").lowercase() + ".")
            return false
        }
        if (!inInventory(player, Items.HAMMER_2347, 1) && !isActive(SkillcapePerks.BAREFISTED_SMITHING, player)) {
            sendDialogue(player, "You need a hammer to work the metal with.")
            return false
        }
        if (!hasRequirement(player, "The Tourist Trap", false) && bars.smithingType == SmithingType.TYPE_DART_TIP) {
            sendDialogue(player, "You need to complete Tourist Trap to smith dart tips.")
            return false
        }
        if (!hasRequirement(player, "Death Plateau", false) && bars.smithingType == SmithingType.TYPE_CLAWS) {
            sendDialogue(player, "You need to complete Death Plateau to smith claws.")
            return false
        }

        return true
    }

    override fun animate() {
        animate(player, Animations.HUMAN_ANVIL_HAMMER_SMITHING_898)
    }

    override fun reward(): Boolean {
        if (delay == 1) {
            delay = 4
            return false
        }
        removeItem(player, Item(bars.barType.bar, bars.smithingType.requiredBar))

        val item = Item(node!!.id, bars.smithingType.productAmount)
        player.inventory.add(item)
        player.dispatch(ResourceProducedEvent(item.id, 1, player, bars.barType.bar))
        rewardXP(player, Skills.SMITHING, bars.barType.experience * bars.smithingType.requiredBar)

        val message = if (StringUtils.isPlusN(ItemDefinition.forId(bars.product).name.lowercase())) "an" else "a"
        sendMessage(player, "You hammer the " + bars.barType.barName.lowercase().replace("smithing", "") + "and make " + message + " " + getItemName(bars.product).lowercase() + ".")

        amount--
        return amount < 1
    }

    override fun message(type: Int) {
    }

}
