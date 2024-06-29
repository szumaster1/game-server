package content.global.skill.production.crafting.item

import content.global.skill.production.crafting.data.PotteryData
import core.api.*
import core.api.consts.Animations
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.diary.DiaryType
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import java.util.*

class FirePotteryPulse(player: Player?, node: Item?, val pottery: PotteryData, var amount: Int) : SkillPulse<Item?>(player, node) {

    var ticks = 0
    override fun checkRequirements(): Boolean {
        if (getStatLevel(player, Skills.CRAFTING) < pottery.level) {
            sendMessage(player, "You need a crafting level of " + pottery.level + " in order to do this.")
            return false
        }
        if (!player.inventory.containsItem(pottery.unfinished)) {
            sendMessage(player, "You need a " + pottery.name.lowercase(Locale.getDefault()) + "in order to do this.")
            return false
        }
        return true
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            player.animate(ANIMATION)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (player.inventory.remove(pottery.unfinished)) {
            val item = pottery.product
            player.inventory.add(item)
            rewardXP(player, Skills.CRAFTING, pottery.fireExp)
            sendMessage(player, "You put the " + pottery.unfinished.name.lowercase() + " in the oven.")
            sendMessage(player, "You remove a " + pottery.product.name.lowercase() + " from the oven.")

            /*
             * Spin a bowl on the pottery wheel and fire it in the oven in Barbarian Village.
             */
            if (pottery == PotteryData.BOWL && withinDistance(player, Location(3085, 3408, 0)) && getAttribute(player, "diary:varrock:spun-bowl", false)) {
                finishDiaryTask(player, DiaryType.VARROCK, 0, 9)
            }
            /*
             * Fire a pot in the kiln in the Barbarian Village potter's house.
             */
            if (pottery == PotteryData.POT && withinDistance(player, Location(3085, 3408, 0))) {
                player.achievementDiaryManager.finishTask(player, DiaryType.LUMBRIDGE, 0, 8)
            }
        }
        amount--
        return amount < 1
    }

    companion object {
        private val ANIMATION = Animation(Animations.OLD_FURNACE_ANIMATION_899)
    }
}
