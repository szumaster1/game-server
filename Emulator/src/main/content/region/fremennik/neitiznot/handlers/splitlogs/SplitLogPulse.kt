package content.region.fremennik.neitiznot.handlers.splitlogs

import cfg.consts.Animations
import cfg.consts.Items
import core.api.*
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillPulse
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Represents a pulse for splitting logs.
 */
class SplitLogPulse(player: Player?, node: Item?, private var amount: Int) : SkillPulse<Item?>(player, node) {

    private val ARCTIC_PINE_LOG = Items.ARCTIC_PINE_LOGS_10810
    private val splitLog: Item = Item(Items.SPLIT_LOG_10812)
    private val splittingAnimation = Animation(Animations.HUMAN_SPLIT_LOGS_5755)

    private var ticks = 0

    override fun checkRequirements(): Boolean {
        if (!inInventory(player, Items.ARCTIC_PINE_LOGS_10810)) {
            sendMessage(player, "You don't have required items in your inventory.")
            return false
        }
        return true
    }

    override fun animate() {
        if (ticks % 5 == 0) {
            animate(player, splittingAnimation)
        }
    }

    override fun reward(): Boolean {
        if (++ticks % 5 != 0) {
            return false
        }
        if (removeItem(player, ARCTIC_PINE_LOG)) {
            addItem(player, splitLog.id, 1)
            rewardXP(player, Skills.WOODCUTTING, 42.5)
            sendMessage(player, "You make a split log of Arctic pine.")
        }
        amount--
        if (amount < 1)
            sendMessage(player, "You have run out of an Arctic pine log.")
        return true
    }
}
