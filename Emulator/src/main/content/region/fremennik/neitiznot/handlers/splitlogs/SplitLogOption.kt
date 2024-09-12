package content.region.fremennik.neitiznot.handlers.splitlogs

import cfg.consts.Items
import cfg.consts.Scenery
import content.data.skill.SkillingTool
import core.api.*
import core.cache.def.impl.SceneryDefinition
import core.game.dialogue.SkillDialogueHandler
import core.game.interaction.OptionHandler
import core.game.node.Node
import core.game.node.entity.player.Player
import core.game.node.item.Item
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Handles interaction with Woodcutting stump.
 */
@Initializable
class SplitLogOption : OptionHandler() {

    override fun newInstance(arg: Any?): Plugin<Any> {
        SceneryDefinition.forId(Scenery.WOODCUTTING_STUMP_21305).handlers["option:cut-wood"] = this
        return this
    }

    override fun handle(player: Player, node: Node, option: String): Boolean {
        if (!isEligibleForSplitting(player)) return false

        if (option == "cut-wood") {
            openSkillDialogue(player, node)
        }
        return true
    }

    private fun isEligibleForSplitting(player: Player): Boolean {
        return when {
            SkillingTool.getHatchet(player) == null -> {
                sendDialogue(player, "You need an axe in order to do this.")
                false
            }
            else -> true
        }
    }

    private fun openSkillDialogue(player: Player, node: Node) {
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
                return amountInInventory(player, node.id)
            }
        }
        dialogue.open()
    }

    companion object {
        private val splitLog: Item = Item(Items.SPLIT_LOG_10812)
        private val fremennikShield: Item = Item(Items.FREMENNIK_ROUND_SHIELD_10826)
    }
}
