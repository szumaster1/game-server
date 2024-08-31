package content.region.fremennik.neitiznot.handlers

import content.data.skill.SkillingTool
import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

/**
 * Represents the Neitiznot listeners.
 */
class NeitiznotListeners : InteractionListener {

    companion object {
        private const val WC_STUMP = Scenery.WOODCUTTING_STUMP_21305
        private const val ARCTIC_PINE = Items.ARCTIC_PINE_LOGS_10810
        private const val SPLIT_LOG = Items.SPLIT_LOG_10812
        private const val FREMENNIK_SHIELD =  Items.FREMENNIK_ROUND_SHIELD_10826
        private val USE_ANIMATION = Animation(Animations.HUMAN_SPLIT_LOGS_5755)
    }

    override fun defineListeners() {

        /*
         * Handles the action of using Arctic Pine logs on the Woodcutting Stump.
         */

        onUseWith(IntType.SCENERY, ARCTIC_PINE, WC_STUMP) { player, _, _ ->
            getLogSplit(player)
            return@onUseWith true
        }

        /*
         * Handles the action of cutting wood at the Woodcutting Stump.
         */

        on(WC_STUMP, IntType.SCENERY, "Cut-wood") { player, _ ->
            getLogSplit(player)
            return@on true
        }
    }

    private fun getLogSplit(player: Player): Boolean {
        if (SkillingTool.getHatchet(player) == null) {
            sendDialogue(player, "You need an axe in order to do this.")
            return false
        }
        if(getStatLevel(player, Skills.WOODCUTTING) < 54){
            sendMessage(player,"You need a woodcutting level of 54 in order to do this.")
            return false
        }

        val animDuration = animationDuration(USE_ANIMATION)
        openDialogue(player, object : DialogueFile() {
            override fun handle(componentID: Int, buttonID: Int) {
                when (stage) {
                    0 -> sendDoubleItemOptions(player, "What would you like to make?", FREMENNIK_SHIELD, SPLIT_LOG, "Fremennik round shield", "Arctic pine split log").also { stage++ }
                    1 -> when (buttonID) {
                        1 -> {
                            end()
                            player.pulseManager.run(FremennikShieldPulse(player, Item(FREMENNIK_SHIELD), 1))
                        }

                        2 -> {
                            end()
                            queueScript(player, 1, QueueStrength.SOFT) {
                                if (!removeItem(player, Item(ARCTIC_PINE, 1))) {
                                    sendMessage(player, "You have run out of an Arctic pine log.")
                                    return@queueScript stopExecuting(player)
                                } else {
                                    addItemOrDrop(player, SPLIT_LOG, 1)
                                    sendMessage(player, "You make a split log of Arctic pine.")
                                    animate(player, USE_ANIMATION)
                                    rewardXP(player, Skills.WOODCUTTING, 42.5)
                                    return@queueScript delayScript(player, animDuration)
                                }
                            }
                        }
                    }
                }
            }
        })
        return true
    }

}