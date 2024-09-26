package content.global.skill.crafting.items.armour.leather

import content.global.skill.crafting.Leather
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils
import org.rs.consts.Animations

/**
 * Represents a pulse used to craft soft leather.
 */
class SoftLeatherCraftingPulse(player: Player?, node: Item?, val soft: Leather.SoftLeather, var amount: Int) :
    SkillPulse<Item?>(player, node) {

    var ticks = 0
    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < soft.level) {
            sendDialogue(player, "You need a crafting level of " + soft.level + " to make " + (if (StringUtils.isPlusN(soft.product.name)) "an" else "a" + " " + soft.product.name).lowercase() + ".")
            return false
        }
        if (!inInventory(player, Leather.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player, Leather.LEATHER, 1)) {
            return false
        }
        if (!inInventory(player, Leather.THREAD.id)) {
            sendDialogue(player, "You need thread to make this.")
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
        if (removeItem(player, Item(Leather.LEATHER))) {
            if (soft == Leather.SoftLeather.GLOVES || soft == Leather.SoftLeather.BOOTS || soft == Leather.SoftLeather.VAMBRACES) {
                sendMessage(player, "You make a pair of " + soft.product.name.lowercase() + ".")
            } else {
                sendMessage(player, "You make " + (if (StringUtils.isPlusN(soft.product.name)) "an " else "a ") + soft.product.name.lowercase() + ".")
            }
            val item = soft.product.id
            addItem(player, item)
            rewardXP(player, Skills.CRAFTING, soft.experience)
            Leather.decayThread(player)
            if (Leather.isLastThread(player)) {
                Leather.removeThread(player)
            }
            if (soft == Leather.SoftLeather.GLOVES) {
                finishDiaryTask(player, DiaryType.LUMBRIDGE, 1, 3)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private const val ANIMATION = Animations.CRAFT_LEATHER_1249
    }
}
