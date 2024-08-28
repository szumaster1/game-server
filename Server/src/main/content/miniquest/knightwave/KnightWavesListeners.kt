package content.miniquest.knightwave

import content.miniquest.knightwave.npc.KnightNPC
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.tools.RED

/**
 * Represents The Knight Waves Training Grounds.
 */
class KnightWavesListeners : InteractionListener {

    override fun defineListeners() {
        on(KnightWaves.DOORS, IntType.SCENERY, "open") { player, node ->
            if (!hasRequirement(player, "King's Ransom")) return@on true
            if (getAttribute(player, KnightWaves.KW_COMPLETE, false)) {
                sendMessage(player, RED + "This miniquest can only be done once.")
                return@on false
            }
            if(player.location.x >= 2752){
                teleport(player,Location.create(2751, 3507, 2))
                return@on true
            }
            teleport(player, Location.create(2753, 3507, 2))
            GameWorld.Pulser.submit(object : Pulse(1, node) {
                override fun pulse(): Boolean {
                    KnightNPC(player.getAttribute(KnightWaves.KW_TIER, 6177), location(2759, 3509, 2), player).init()
                    sendMessageWithDelay(player, "Remember, only melee combat is allowed in here.", 1)
                    return true
                }
            })
            return@on true
        }
    }
}