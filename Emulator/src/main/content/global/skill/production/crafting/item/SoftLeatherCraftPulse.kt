package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.Leather
import content.global.skill.production.crafting.data.Leather.SoftLeather
import content.global.skill.production.crafting.data.Leather.decayThread
import content.global.skill.production.crafting.data.Leather.isLastThread
import content.global.skill.production.crafting.data.Leather.removeThread
import core.api.*
import org.rs.consts.Animations
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils

/**
 * Represents a pulse used to craft soft leather.
 */
class SoftLeatherCraftPulse(player: Player?, node: Item?, val soft: SoftLeather, var amount: Int) :
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
            if (soft == SoftLeather.GLOVES || soft == SoftLeather.BOOTS || soft == SoftLeather.VAMBRACES) {
                sendMessage(player, "You make a pair of " + soft.product.name.lowercase() + ".")
            } else {
                sendMessage(player,"You make " + (if (StringUtils.isPlusN(soft.product.name)) "an " else "a ") + soft.product.name.lowercase() + ".")
            }
            val item = soft.product.id
            addItem(player, item)
            rewardXP(player, Skills.CRAFTING, soft.experience)
            decayThread(player)
            if (isLastThread(player)) {
                removeThread(player)
            }
            if (soft == SoftLeather.GLOVES) {
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
