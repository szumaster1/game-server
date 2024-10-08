package content.global.skill.crafting.pottery

import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import org.rs.consts.Animations

/**
 * Represents the pulse used to fire pottery.
 */
class FirePotteryPulse(player: Player?, node: Item?, val pottery: Pottery, var amount: Int) :
    SkillPulse<Item?>(player, node) {

    var ticks = 0
    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < pottery.level) {
            sendMessage(player, "You need a crafting level of " + pottery.level + " in order to do this.")
            return false
        }
        if (!inInventory(player, pottery.unfinished.id)) {
            sendMessage(player, "You don't have any more ${pottery.name.lowercase()} which need firing.")
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
        if (removeItem(player, pottery.unfinished)) {
            val item = pottery.product
            addItem(player, item.id, item.amount)
            rewardXP(player, Skills.CRAFTING, pottery.fireExp)
            sendMessage(player, "You put the " + getItemName(pottery.unfinished.id).lowercase() + " in the oven.")
            sendMessage(player, "You remove a " + getItemName(pottery.product.id).lowercase() + " from the oven.")

            /*
             * Spin a bowl on the pottery wheel and fire it in the oven in Barbarian Village.
             */
            if (pottery == Pottery.BOWL && withinDistance(player, Location(3085, 3408, 0)) && getAttribute(player, "diary:varrock:spun-bowl", false)) {
                finishDiaryTask(player, DiaryType.VARROCK, 0, 9)
            }
            /*
             * Fire a pot in the kiln in the Barbarian Village potter's house.
             */
            if (pottery == Pottery.POT && withinDistance(player, Location(3085, 3408, 0))) {
                finishDiaryTask(player, DiaryType.LUMBRIDGE, 0, 8)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private const val ANIMATION = Animations.USE_FURNACE_3243
    }
}
