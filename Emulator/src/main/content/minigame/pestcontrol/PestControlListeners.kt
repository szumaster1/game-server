package content.minigame.pestcontrol

import content.global.travel.charter.Ship
import cfg.consts.NPCs
import core.api.getUsedOption
import core.api.openDialogue
import core.cache.def.impl.NPCDefinition
import core.game.interaction.IntType
import core.game.interaction.InteractionListener

/**
 * Represents the Pest control listeners.
 */
class PestControlListeners : InteractionListener {

    override fun defineListeners() {

        on(VOID_KNIGHT, IntType.NPC, "exchange") { player, _ ->
            PCRewardInterface.open(player)
            return@on true
        }

        on(SQUIRE, IntType.NPC, "talk-to", "leave") { player, node ->
            val session = node.asNpc().getExtension<PestControlSession>(PestControlSession::class.java)
            when (getUsedOption(player)) {
                "talk-to" -> {
                    if (session == null) {
                        val handler = NPCDefinition.getOptionHandlers()[getUsedOption(player)]
                        handler!!.handle(player, node, getUsedOption(player))
                        return@on true
                    } else {
                        openDialogue(player, SQUIRE, node, true)
                    }
                }

                "leave" -> {
                    if (session == null) {
                        Ship.PEST_TO_PORT_SARIM.sail(player)
                        return@on true
                    }
                    player.properties.teleportLocation = session.activity.leaveLocation
                }
            }
            return@on true
        }

    }

    companion object {
        const val SQUIRE = NPCs.SQUIRE_3781
        val VOID_KNIGHT =
            intArrayOf(
                NPCs.VOID_KNIGHT_3786,
                NPCs.VOID_KNIGHT_3788,
                NPCs.VOID_KNIGHT_3789,
                NPCs.VOID_KNIGHT_5956
            )
    }

}