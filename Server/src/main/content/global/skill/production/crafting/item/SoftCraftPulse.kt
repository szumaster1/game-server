package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.LeatherData
import content.global.skill.production.crafting.data.LeatherData.SoftLeather
import content.global.skill.production.crafting.data.LeatherData.decayThread
import content.global.skill.production.crafting.data.LeatherData.isLastThread
import content.global.skill.production.crafting.data.LeatherData.removeThread
import core.api.*
import core.api.consts.Animations
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.tools.StringUtils

/**
 * Soft craft pulse
 *
 * @property soft Represents the soft leather used in crafting.
 * @property amount Indicates the quantity of soft leather to be used.
 * @constructor Initializes a SoftCraftPulse instance.
 *
 * @param player The player who is performing the crafting action.
 * @param node The item node associated with the crafting process.
 */
class SoftCraftPulse(player: Player?, node: Item?, val soft: SoftLeather, var amount: Int) :
    SkillPulse<Item?>(player, node) {

    var ticks = 0
    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < soft.level) {
            sendDialogue(player, "You need a crafting level of " + soft.level + " to make " + (if (StringUtils.isPlusN(soft.product.name)) "an" else "a" + " " + soft.product.name).lowercase() + ".")
            return false
        }
        if (!inInventory(player, LeatherData.NEEDLE, 1)) {
            return false
        }
        if (!inInventory(player, LeatherData.LEATHER, 1)) {
            return false
        }
        if (!inInventory(player, LeatherData.THREAD.id)) {
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
        if (removeItem(player, Item(LeatherData.LEATHER))) {
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
