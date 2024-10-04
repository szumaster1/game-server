package content.global.skill.smithing

import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.event.ResourceProducedEvent
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.QuestName

/**
 * Represents the smithing pulse.
 */
class SmithingPulse(player: Player?, item: Item?, private val bars: Bars, private var amount: Int) :
    SkillPulse<Item?>(player, item) {

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, bars.barType.bar, bars.smithingType.bar * amount)) {
            amount = amountInInventory(player, bars.barType.bar)
        }
        closeInterface(player)
        if (getStatLevel(player, Skills.SMITHING) < bars.level) {
            sendDialogue(player, "You need a Smithing level of " + bars.level + " to make a " + getItemName(bars.product) + ".")
            return false
        }
        if (!inInventory(player, bars.barType.bar, bars.smithingType.bar)) {
            sendDialogue(player, "You don't have enough " + getItemName(bars.barType.bar).lowercase() + "s to make a " + bars.smithingType.name.replace("TYPE_", "").replace("_", " ").lowercase() + ".")
            return false
        }
        if (!inInventory(player, Items.HAMMER_2347, 1)) {
            sendDialogue(player, "You need a hammer to work the metal with.")
            return false
        }
        if (!hasRequirement(player, QuestName.THE_TOURIST_TRAP, false) && bars.smithingType == SmithingType.TYPE_DART_TIP) {
            sendDialogue(player, "You need to complete Tourist Trap to smith dart tips.")
            return false
        }
        if (!hasRequirement(player, QuestName.DEATH_PLATEAU, false) && bars.smithingType == SmithingType.TYPE_CLAWS) {
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
        removeItem(player, Item(bars.barType.bar, bars.smithingType.bar))

        val item = Item(node!!.id, bars.smithingType.amount)
        player.inventory.add(item)
        player.dispatch(ResourceProducedEvent(item.id, 1, player, bars.barType.bar))
        rewardXP(player, Skills.SMITHING, bars.barType.experience * bars.smithingType.bar)

        val message = if (StringUtils.isPlusN(ItemDefinition.forId(bars.product).name.lowercase())) "an" else "a"
        sendMessage(player, "You hammer the " + bars.barType.barName.lowercase().replace("smithing", "") + "and make " + message + " " + getItemName(bars.product).lowercase() + ".")

        amount--
        return amount < 1
    }

    override fun message(type: Int) {
    }

}
