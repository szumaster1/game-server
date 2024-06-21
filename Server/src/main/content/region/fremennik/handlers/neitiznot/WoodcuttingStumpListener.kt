package content.region.fremennik.handlers.neitiznot

import content.data.skill.SkillingTool
import core.api.*
import core.api.consts.Items
import core.api.consts.Scenery
import core.game.dialogue.DialogueFile
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.world.update.flag.context.Animation

class WoodcuttingStumpListener : InteractionListener {

    companion object {
        private const val WC_STUMP = Scenery.WOODCUTTING_STUMP_21305
        private const val ARCTIC_PINE = Items.ARCTIC_PINE_LOGS_10810
        private const val SPLIT_LOG = Items.SPLIT_LOG_10812
        private const val FREMENNIK_SHIELD =  Items.FREMENNIK_ROUND_SHIELD_10826
        private val USE_ANIMATION = Animation(5755)
    }


    override fun defineListeners() {
        onUseWith(IntType.SCENERY, ARCTIC_PINE, WC_STUMP) { player, _, _ ->
            getLogsSplit(player)
            return@onUseWith true
        }

        on(WC_STUMP, IntType.SCENERY, "Cut-wood") { player, _ ->
            getLogsSplit(player)
            return@on true
        }
    }

    private fun getLogsSplit(player: Player): Boolean {
        if (SkillingTool.getHatchet(player) == null && getStatLevel(player, Skills.WOODCUTTING) >= 54) {
            sendDialogue(player, "You need an axe in order to do this.")
            return false
        }

        lock(player, duration = Animation(5755).duration + 1)
        lockInteractions(player, duration = Animation(5755).duration + 1)
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
                            lock(player, USE_ANIMATION.duration + 1)
                            lockInteractions(player, USE_ANIMATION.duration + 1)
                            queueScript(player, 1, QueueStrength.SOFT) {
                                if (!removeItem(player, Item(ARCTIC_PINE, 1))) {
                                    unlock(player)
                                    sendMessage(player, "You have run out of an Arctic pine log.")
                                    return@queueScript stopExecuting(player)
                                } else {
                                    addItemOrDrop(player, SPLIT_LOG, 1)
                                    sendMessage(player, "You make a split log of Arctic pine.")
                                    animate(player, USE_ANIMATION)
                                    rewardXP(player, Skills.WOODCUTTING, 42.5)
                                    return@queueScript delayScript(player, Animation(5755).duration)
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