package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.PotteryData
import core.api.*
import core.api.consts.Animations
import core.api.consts.Items
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.StringUtils

/**
 * Pottery craft pulse
 *
 * @property amount
 * @property pottery
 * @constructor
 *
 * @param player
 * @param node
 */
class PotteryCraftPulse(player: Player?, node: Item?, var amount: Int, val pottery: PotteryData) : SkillPulse<Item?>(player, node) {

    var ticks = 0

    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < pottery.level) {
            sendMessage(player, "You need a crafting level of " + pottery.level + " to make this.")
            return false
        }
        if (!inInventory(player, Items.SOFT_CLAY_1761, 1)) {
            sendMessage(player, "You need soft clay in order to do this.")
            return false
        }
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
        if (player.inventory.remove(SOFT_CLAY)) {
            if (pottery == PotteryData.BOWL && withinDistance(player, Location(3086, 3410, 0))) {
                setAttribute(player, "/save:diary:varrock:spun-bowl", true)
            }
            val item = pottery.unfinished
            player.inventory.add(item)
            rewardXP(player, Skills.CRAFTING, pottery.exp)
            sendMessage(player, "You make the soft clay into " + (if (StringUtils.isPlusN(pottery.unfinished.name)) "an" else "a") + " " + pottery.unfinished.name.lowercase() + ".")

            if (pottery == PotteryData.POT && withinDistance(player, Location(3086, 3410, 0))) {
                finishDiaryTask(player, DiaryType.LUMBRIDGE, 0, 7)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private val ANIMATION = Animation.create(Animations.HUMAN_COOKING_RANGE_896)
        private val SOFT_CLAY = Item(Items.SOFT_CLAY_1761)
    }
}
