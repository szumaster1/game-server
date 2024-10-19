package content.region.kandarin.quest.tol.handlers

import core.api.*
import core.game.dialogue.FacialExpression
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.tools.RandomFunction
import org.rs.consts.*

/**
 * Tower of life listener.
 */
class TowerOfLifeListener : InteractionListener {
    companion object {
        const val TOWER_DOORS = Scenery.TOWER_DOOR_21814
        const val PLANTS = Scenery.PLANT_21924
        const val PLANTS_SEARCH = Animations.HUMAN_PLANT_SEARCH_5819
    }

    override fun defineListeners() {
        on(TOWER_DOORS, IntType.SCENERY, "open") { player, node ->
            if(!hasRequirement(player, QuestName.TOWER_OF_LIFE)) return@on true
            DoorActionHandler.handleAutowalkDoor(player, node.asScenery())
            if(player.location.y == 3225) sendPlayerDialogue(
                player,
                "Wow, this place looks special. Best I look around for something to fix.",
                FacialExpression.AMAZED
            )
            return@on true
        }

        on(PLANTS, IntType.SCENERY, "search") { player, _ ->
            val random = RandomFunction.random(1, 5)
            lock(player, 2)
            animate(player, PLANTS_SEARCH)
            sendMessage(player, "You search the plant...")

            queueScript(player, 3, QueueStrength.WEAK) {
                when (RandomFunction.random(1, 5)) {
                    3 -> {
                        sendDialogue(player, "Aha!, You find some trousers!")
                        sendMessage(player, "Try the beckon emote while wearing an item of builders' clothing!")
                        return@queueScript stopExecuting(player)
                    }

                    else -> {
                        sendMessage(player, "Nope, nothing here.")
                        return@queueScript stopExecuting(player)
                    }
                }
            }
            return@on true
        }

        //      0 -> player("Why does nobody listen?").also { stage++ }
        //		1 -> player("Best I follow them, I suppose.").also { stage = END_DIALOGUE }

    }
}

