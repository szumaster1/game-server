package content.region.fremennik.neitiznot.handlers.splitlogs

import cfg.consts.Items
import cfg.consts.Scenery
import core.api.*
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.NodeUsageEvent
import core.game.interaction.UseWithHandler
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles the splitting Arctic pine logs when they are used on a Woodcutting stump.
 */
@Initializable
class SplitLog : UseWithHandler(ARCTIC_PINE_LOG) {

    override fun newInstance(arg: Any?): Plugin<Any> {
        addHandler(WOODCUTTING_STUMP, OBJECT_TYPE, this)
        return this
    }

    override fun handle(event: NodeUsageEvent): Boolean {
        val player = event.player
        if (getStatLevel(player, Skills.WOODCUTTING) < 54) {
            sendMessage(player, "You need a woodcutting level of 54 in order to do this.")
            return false
        }
        if (!inInventory(player, Items.ARCTIC_PINE_LOGS_10810)) {
            sendMessage(player, "You don't have required items in your inventory.")
            return false
        }

        val dialogue = object : SkillDialogueHandler(player, SkillDialogue.TWO_OPTION, fremennikShield, splitLog) {
            override fun create(amount: Int, index: Int) {
                submitIndividualPulse(
                    player,
                    if (index == 1) {
                        FremennikShieldPulse(player, fremennikShield, amount)
                    } else {
                        SplitLogPulse(player, splitLog, amount)
                    }
                )
            }

            override fun getAll(index: Int): Int {
                return amountInInventory(event.player, event.usedItem.id)
            }
        }
        dialogue.open()
        return true
    }

    companion object {
        const val ARCTIC_PINE_LOG = Items.ARCTIC_PINE_LOGS_10810
        const val WOODCUTTING_STUMP = Scenery.WOODCUTTING_STUMP_21305
        val splitLog: Item = Item(Items.SPLIT_LOG_10812)
        val fremennikShield: Item = Item(Items.FREMENNIK_ROUND_SHIELD_10826)
    }
}