package content.region.misthalin.handlers.varrock

import content.region.misthalin.dialogue.varrock.SawmillOperatorDialogue
import core.api.*
import core.api.consts.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.npc.NPC
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Lumberyard listeners.
 */
class LumberyardListeners : InteractionListener {

    companion object {
        private val CRATE = intArrayOf(Scenery.CRATE_767, Scenery.CRATE_2620)
        private val SQUEEZE_UNDER_ANIM = Animation.create(Animations.ENTER_LUMBER_YARD_9221)
        private const val SAWMILL_OPERATOR = NPCs.SAWMILL_OPERATOR_4250
        private const val BROKEN_FENCE = Scenery.FENCE_31149
    }

    override fun defineListeners() {

        /**
         * Gertrude's Cat quest related interaction.
         */
        on(CRATE, IntType.SCENERY, "search") { player, node ->

            if (getQuestStage(player, "Gertrude's Cat") == 50 && hasAnItem(player, Items.THREE_LITTLE_KITTENS_13236).container != null) {
                setQuestStage(player, "Gertrude's Cat", 40)
            }

            if (node is NPC) {
                sendMessage(player, "You search the crate.")
                sendMessage(player, "You find nothing.")
            }

            if (getQuestStage(player, "Gertrude's Cat") == 40) {
                if (getAttribute(player, "findkitten", false) && freeSlots(player) > 0) {
                    setQuestStage(player, "Gertrude's Cat", 50)
                    sendDialogue(player, "You find a kitten! You carefully place it in your backpack.")
                    addItem(player, Items.THREE_LITTLE_KITTENS_13236)
                }
                sendMessage(player, "You search the crate.")
                sendMessage(player, "You find nothing.")
                if (RandomFunction.random(0, 3) == 1) {
                    sendMessage(player, "You can hear kittens mewing close by...")
                    setAttribute(player, "findkitten", true)
                }
            } else {
                sendMessage(player, "You search the crate.")
                sendMessage(player, "You find nothing.")
            }
            return@on true
        }

        /**
         * Squeeze-under hole on the yard's west wall.
         */
        on(BROKEN_FENCE, IntType.SCENERY, "squeeze-under") { player, node ->
            lock(player, 1)
            queueScript(player, 0, QueueStrength.SOFT) { stage: Int ->
                when (stage) {
                    0 -> {
                        if (player.location.y != 3498)
                            forceWalk(player, node.location, "Smart")
                        return@queueScript keepRunning(player)
                    }

                    1 -> {
                        forceMove(
                            player,
                            player.location,
                            player.location.transform(
                                if (player.location.x < 3296) Direction.EAST else Direction.WEST, 1
                            ),
                            0,
                            30,
                            null,
                            SQUEEZE_UNDER_ANIM.id
                        )
                        return@queueScript stopExecuting(player)

                    }

                    else -> return@queueScript stopExecuting(player)
                }
            }
            return@on true
        }

        /**
         * Sawmill operator option listeners.
         */
        on(SAWMILL_OPERATOR, IntType.NPC, "talk-to") { player, _ ->
            openDialogue(player, SawmillOperatorDialogue())
            return@on true
        }

        on(SAWMILL_OPERATOR, IntType.NPC, "buy-plank") { player, _ ->
            openInterface(player, Components.POH_SAWMILL_403)
            return@on true
        }

        on(SAWMILL_OPERATOR, IntType.NPC, "trade") { player, _ ->
            openNpcShop(player, SAWMILL_OPERATOR)
            return@on true
        }

        setDest(IntType.NPC, intArrayOf(SAWMILL_OPERATOR), "talk-to", "buy-plank", "trade") { _, _ ->
            return@setDest Location.create(3302, 3491, 0)
        }
    }

}
