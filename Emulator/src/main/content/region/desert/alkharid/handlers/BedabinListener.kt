package content.region.desert.alkharid.handlers

import org.rs.consts.NPCs
import org.rs.consts.Scenery
import core.api.findNPC
import core.api.getScenery
import core.api.replaceScenery
import core.api.sendMessage
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.world.map.Location

class BedabinListener : InteractionListener {

    override fun defineListeners() {

        /*
         * Listener for walking through the tent
         */

        on(TENT, IntType.SCENERY, "walk-through") { player, _ ->
            if (player.location.y >= 3046) {
                val door = getScenery(Location(3169, 3046, 0))
                if (door!!.id != 2701) replaceScenery(door.asScenery(), 2701, 2)
                player.walkingQueue.reset()
                player.walkingQueue.addPath(3169, 3045)
                sendMessage(player,"You walk back out the tent.")
                return@on true
            }
            player.dialogueInterpreter.open(834, findNPC(834))
            return@on true
        }

        /*
         * Listener for using the experimental anvil
         */

        on(EXPERIMENTAL_ANVIL, IntType.SCENERY, "use") { player, _ ->
            sendMessage(player, "To forge items use the metal you wish to work with the anvil.")
            return@on true
        }

        /*
         * Listener for talking to the Nomad Guard NPC
         */

        on(NOMAD_GUARD, IntType.NPC, "talk-to") { player, node ->
            player.dialogueInterpreter.open(node.id, node.asNpc())
            return@on true
        }

    }

    override fun defineDestinationOverrides() {
        /*
         * Set destination for talking to the Bedabin Nomad Guard NPC
         */

        setDest(IntType.NPC, intArrayOf(NPCs.BEDABIN_NOMAD_GUARD_834), "talk-to") { _, _ ->
            return@setDest Location(3169, 3045, 0)
        }
    }

    companion object {
        private const val TENT = Scenery.TENT_DOOR_2700
        private const val EXPERIMENTAL_ANVIL = Scenery.AN_EXPERIMENTAL_ANVIL_2672
        private const val NOMAD_GUARD = NPCs.BEDABIN_NOMAD_GUARD_834
    }
}