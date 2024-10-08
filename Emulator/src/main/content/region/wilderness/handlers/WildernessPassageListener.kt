package content.region.wilderness.handlers

import core.api.hasRequirement
import core.api.openInterface
import core.api.sendMessage
import core.api.teleport
import core.game.component.Component
import core.game.global.action.ClimbActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location
import org.rs.consts.Components
import org.rs.consts.QuestName
import org.rs.consts.Scenery

class WildernessPassageListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Handles enter to the Corporeal Beast cavern.
         */

        on(Scenery.ENTRANCE_37749, IntType.SCENERY, "go-through") { player, _ ->
            teleport(player, Location.create(2885, 4372, 2))
            return@on true
        }

        /*
         * Handles exit from the Corporeal Beast cavern.
         */

        on(Scenery.EXIT_37928, IntType.SCENERY, "go-through") { player, _ ->
            teleport(player, Location.create(3214, 3782, 0))
            return@on true
        }

        on(intArrayOf(Scenery.PASSAGE_37929,Scenery.PASSAGE_38811), IntType.SCENERY, "go-through") { player, node ->
            if (player.location.x < node.location.x) {
                openInterface(player, Components.CWS_WARNING_30_650)
            } else {
                teleport(
                    entity = player,
                    loc = player.location.transform(if (player.location.x < node.location.x) 4 else -4, 0, 0)
                )
            }
            return@on true
        }

        /*
         * Handles enter to the chaos temple -1 (Armoured Zombies).
         */

        on(Scenery.TRAPDOOR_39188, IntType.SCENERY, "open") { player, _ ->
            if (!hasRequirement(player, QuestName.DEFENDER_OF_VARROCK))
                ClimbActionHandler.climb(
                    player,
                    ClimbActionHandler.CLIMB_DOWN,
                    Location.create(3241, 9991, 0),
                    "You descend into the cavern below."
                )
            return@on true
        }

        /*
         * Exit the Chaos temple dungeon.
         */

        on(Scenery.LADDER_39191, IntType.SCENERY, "climb-up") { player, _ ->
            ClimbActionHandler.climb(
                player,
                ClimbActionHandler.CLIMB_UP,
                Location.create(3239, 3606, 0),
                "You climb up the ladder to the surface."
            );
            return@on true
        }

        /*
         * Lock the blind door.
         */
        on(Scenery.DOOR_39200, IntType.SCENERY, "open") { player, _ ->
            sendMessage(player, "The door doesn't open.")
            return@on true
        }

    }

}