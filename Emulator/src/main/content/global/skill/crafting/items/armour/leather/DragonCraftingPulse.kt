package content.global.skill.crafting.items.armour.leather

import content.global.skill.crafting.Leather
import core.api.*
import core.cache.def.impl.ItemDefinition
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils
import org.rs.consts.Animations

/**
 * Represents a pulse used to craft dragon leather.
 */
class DragonCraftingPulse(player: Player?, node: Item?, val hide: Leather.DragonHide, var amount: Int) :
    SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < hide.level) {
            sendDialogue(player, "You need a crafting level of " + hide.level + " to make " + ItemDefinition.forId(hide.product).name + ".")
            amount = 0
            return false
        }
        if (!inInventory(player, Leather.NEEDLE, 1)) {
            sendDialogue(player, "You need a needle to make this.")
            amount = 0
            return false
        }
        if (!inInventory(player, Leather.THREAD.id)) {
            sendDialogue(player, "You need thread to make this.")
            amount = 0
            return false
        }
        if (!inInventory(player, hide.leather, hide.amount)) {
            sendDialogue(player, "You need " + hide.amount + " " + getItemName(hide.leather).lowercase() + " to make this.")
            amount = 0
            return false
        }
        closeInterface(player)
        return true
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            animate(player, ANIMATION)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (removeItem(player, Item(hide.leather, hide.amount))) {
            if (hide.name.contains("VAMBS")) {
                sendMessage(player, "You make a pair of " + getItemName(hide.product).lowercase() + "'s.")
            } else {
                sendMessage(player, "You make " + (if (StringUtils.isPlusN(getItemName(hide.product).lowercase())) "an" else "a") + " " + getItemName(hide.product).lowercase() + ".")
            }
            val item = Item(hide.product)
            addItem(player, item.id, item.amount)
            rewardXP(player, Skills.CRAFTING, hide.experience)
            Leather.decayThread(player)
            if (Leather.isLastThread(player)) {
                Leather.removeThread(player)
            }
            amount--
        }
        return amount < 1
    }

    override fun message(type: Int) {}

    companion object {
        private const val ANIMATION = Animations.CRAFT_LEATHER_1249
    }
}
