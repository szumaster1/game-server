package content.region.fremennik.neitiznot.handlers.splitlogs

import cfg.consts.Items
import cfg.consts.Scenery
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import kotlin.math.min

class SplitLogListener : InteractionListener {

    companion object {
        const val ARCTIC_PINE_LOG = Items.ARCTIC_PINE_LOGS_10810
        const val SPLIT_LOG = Items.SPLIT_LOG_10812
        const val FREMENNIK_SHIELD = Items.FREMENNIK_ROUND_SHIELD_10826
    }

    override fun defineListeners() {

        /*
         * Handles interaction with Woodcutting stump.
         */

        on(Scenery.WOODCUTTING_STUMP_21305, IntType.SCENERY, "cut-wood") { player, _ ->
            if (getStatLevel(player, Skills.WOODCUTTING) < 54) {
                sendMessage(player, "You need a woodcutting level of 54 in order to do this.")
                return@on true
            }
            if (!inInventory(player, Items.ARCTIC_PINE_LOGS_10810)) {
                sendMessage(player, "You don't have required items in your inventory.")
                return@on true
            }

            sendSkillDialogue(player) {
                withItems(FREMENNIK_SHIELD, SPLIT_LOG)
                create { id, amount ->
                    submitIndividualPulse(
                        player,
                        if (id == FREMENNIK_SHIELD)
                            FremennikShieldPulse(player, Item(ARCTIC_PINE_LOG), amount)
                        else
                            SplitLogPulse(player, Item(ARCTIC_PINE_LOG), amount)
                    )

                }
                calculateMaxAmount { _ ->
                    min(amountInInventory(player, ARCTIC_PINE_LOG), amountInInventory(player, ARCTIC_PINE_LOG))
                }
            }

            return@on true
        }

        /*
         * Handles the splitting Arctic pine logs when they are used on a Woodcutting stump.
         */

        onUseWith(IntType.SCENERY, ARCTIC_PINE_LOG, Scenery.WOODCUTTING_STUMP_21305) { player, used, _ ->
            if (getStatLevel(player, Skills.WOODCUTTING) < 54) {
                sendMessage(player, "You need a woodcutting level of 54 in order to do this.")
                return@onUseWith true
            }
            if (!inInventory(player, Items.ARCTIC_PINE_LOGS_10810)) {
                sendMessage(player, "You don't have required items in your inventory.")
                return@onUseWith true
            }

            sendSkillDialogue(player) {
                withItems(FREMENNIK_SHIELD, SPLIT_LOG)
                create { id, amount ->
                    submitIndividualPulse(
                        player,
                        if (id == FREMENNIK_SHIELD)
                            FremennikShieldPulse(player, Item(ARCTIC_PINE_LOG), amount)
                        else
                            SplitLogPulse(player, Item(ARCTIC_PINE_LOG), amount)
                    )

                }
                calculateMaxAmount { _ ->
                    min(amountInInventory(player, used.id), amountInInventory(player, used.id))
                }
            }

            return@onUseWith true
        }
    }

}