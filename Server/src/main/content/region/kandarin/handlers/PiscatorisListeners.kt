package content.region.kandarin.handlers

import core.api.*
import cfg.consts.Animations
import cfg.consts.Items
import cfg.consts.NPCs
import content.region.kandarin.travel.RowingBoat
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.item.Item
import core.game.node.scenery.Scenery
import core.game.node.scenery.SceneryBuilder
import core.game.system.task.Pulse

/**
 * Piscatoris listeners.
 */
class PiscatorisListeners : InteractionListener {

    companion object {
        private const val NET_SCENERY = 14973
        private const val EMPTY_NET_SCENERY = 14972
        private const val KATHY_CORKAT = NPCs.KATHY_CORKAT_3831
    }

    override fun defineListeners() {

        /*
         * Rowing boat travel interaction.
         */

        on(KATHY_CORKAT, IntType.NPC, "travel") { player, node ->
            RowingBoat.sail(player, node.asNpc())
            return@on true
        }

        /*
         * Net scenery interaction.
         */

        on(NET_SCENERY, IntType.SCENERY, "Take-from") { player, node ->
            if (!hasRequirement(player, "Swan Song")) return@on true

            if (!hasSpaceFor(player, Item(Items.SEAWEED_401, 1))) {
                sendMessage(player, "You do not have space in your inventory.")
                return@on true
            }
            submitIndividualPulse(player, object : Pulse() {
                private var tick = 0
                override fun pulse(): Boolean {
                    when (tick++) {
                        0 -> animate(player, Animations.HUMAN_BURYING_BONES_827)
                        1 -> {
                            if (addItem(player, Items.SEAWEED_401)) {
                                SceneryBuilder.replace(node.asScenery(), Scenery(EMPTY_NET_SCENERY, node.location, node.asScenery().rotation), 5)
                            }
                            return true
                        }
                    }
                    return false
                }
            })
            return@on true
        }
    }
}
