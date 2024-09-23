package content.region.fremennik.lunarisle

import core.api.*
import org.rs.consts.Animations
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.interaction.QueueStrength
import core.game.node.entity.player.link.TeleportManager.TeleportType

/**
 * Represents the Lunar isle listeners.
 */
class LunarIsle : InteractionListener {

    companion object {
        private const val CLOSED_DOOR = Scenery.DOOR_16774
        private const val OPENED_DOOR = Scenery.DOOR_16777
        private const val HOUSE = NPCs.HOUSE_4512
        private const val LADDER_UP = Scenery.LADDER_16640
        private const val LADDER_DOWN = Scenery.LADDER_36306
        private val CYRISUS = intArrayOf(NPCs.CYRISUS_5893, NPCs.CYRISUS_5894, NPCs.CYRISUS_5895, NPCs.CYRISUS_5896, NPCs.CYRISUS_5897)
    }

    override fun defineListeners() {

        // This listener handles the action of opening the closed door.
        on(CLOSED_DOOR, IntType.SCENERY, "open") { player, _ ->
            teleport(player, location(2101, 3926, 0), TeleportType.LUNAR)
            return@on true
        }

        // This listener handles the action of closing the opened door.
        on(OPENED_DOOR, IntType.SCENERY, "close") { player, _ ->
            teleport(player, location(2101, 3926, 0), TeleportType.LUNAR)
            return@on true
        }

        // This listener handles the action of going inside the house.
        on(HOUSE, IntType.NPC, "go-inside") { player, _ ->
            teleport(player, location(2451, 4645, 0), TeleportType.LUNAR)
            return@on true
        }

        // This listener handles the action of climbing up the ladder.
        on(LADDER_UP, IntType.SCENERY, "climb-up") { player, _ ->
            animate(player, Animations.HUMAN_CLIMB_STAIRS_828)
            queueScript(player, 1, QueueStrength.SOFT) {
                return@queueScript teleport(player, location(2141, 3944, 0))
            }
            return@on true
        }

        // This listener handles the action of climbing down the ladder.
        on(LADDER_DOWN, IntType.SCENERY, "climb-down") { player, _ ->
            animate(player, Animations.HUMAN_CLIMB_STAIRS_828)
            queueScript(player, 1, QueueStrength.SOFT) {
                return@queueScript teleport(player, location(2329, 10353, 2))
            }
            return@on true
        }

        // This listener handles the action of using Cave Nightshade item on Cyrissus NPC.
        onUseWith(IntType.NPC, Items.CAVE_NIGHTSHADE_2398, *CYRISUS) { player, _, _ ->
            sendMessage(player, "How evil! Are you trying to kill him?")
            return@onUseWith true
        }
    }
}
