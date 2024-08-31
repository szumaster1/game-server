package content.global.guilds.wizards

import cfg.consts.NPCs
import content.global.travel.EssenceTeleport.teleport
import core.api.*
import core.game.global.action.ClimbActionHandler
import core.game.global.action.DoorActionHandler
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.node.entity.skill.Skills
import core.game.node.scenery.Scenery
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Wizards guild listeners.
 */
@Initializable
class WizardsGuildListeners : InteractionListener {

    override fun defineListeners() {

        /*
         * Handling the scenery interaction.
         */

        on(sceneryIDs, IntType.SCENERY, "open", "climb-up") { player, node ->
            val id = if (node is Scenery) node.id else (node as NPC).id
            when (getUsedOption(player)) {
                "climb-up" -> if (node.location == Location(2590, 3089, 0)) {
                    ClimbActionHandler.climb(player, null, Location.create(2591, 3092, 1))
                } else {
                    ClimbActionHandler.climbLadder(player, node as Scenery, "climb-up")
                }

                "open" -> when (id) {
                    1600, 1601 -> {
                        if (getDynLevel(player, Skills.MAGIC) < 66) {
                            sendPlayerDialogue(player, "You need a Magic level of at least 66 to enter.")
                            return@on true
                        }
                        DoorActionHandler.handleAutowalkDoor(player, node as Scenery)
                    }

                    2155, 2154 -> sendNPCDialogue(player, NPCs.WIZARD_FRUMSCONE_460, "You can't attack the Zombies in the room, my Zombies are for magic target practice only and should be attacked from the other side of the fence.")
                }
            }
            return@on true
        }

        /*
         * Handling the essence teleport inside guild.
         */

        on(npcIDs, IntType.NPC, "teleport") { player, node ->
            if (!isQuestComplete(player, "Rune Mysteries")) {
                sendMessage(player, "You need to have completed the Rune Mysteries Quest to use this feature.")
                return@on false
            }

            teleport((node as NPC), player)
            return@on true
        }
    }

    companion object {
        val sceneryIDs = intArrayOf(1600, 1601, 2154, 2155, 1722)
        val npcIDs = NPCs.WIZARD_DISTENTOR_462
    }

}
