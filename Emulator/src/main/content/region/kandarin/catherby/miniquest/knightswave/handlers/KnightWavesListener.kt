package content.region.kandarin.catherby.miniquest.knightswave.handlers

import content.data.GameAttributes
import core.api.*
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import org.rs.consts.NPCs
import org.rs.consts.QuestName

class KnightWavesListener : InteractionListener {

    override fun defineListeners() {
        on(KnightWavesUtils.DOORS, IntType.SCENERY, "open") { player, node ->
            if (!hasRequirement(player, QuestName.KINGS_RANSOM)) return@on true
            if (getAttribute(player, KnightWavesUtils.KW_COMPLETE, false) || !getAttribute(player, KnightWavesUtils.KW_BEGIN, false)) {
                openDialogue(player, NPCs.SQUIRE_6169)
                return@on false
            }
            /*
             * Leaving area.
             */
            if (player.location.x >= 2752 && getAttribute(player, KnightWavesUtils.KW_BEGIN, false)) {
                teleport(player, Location.create(2751, 3507, 2))
                removeAttributes(player, GameAttributes.PRAYER_LOCK,
                    KnightWavesUtils.KW_SPAWN,
                    KnightWavesUtils.KW_TIER,
                    KnightWavesUtils.KW_BEGIN
                )
                clearLogoutListener(player, "Knight's training")
                return@on true
            }
            /*
             * Enter the area.
             */
            teleport(player, Location.create(2753, 3507, 2))
            registerLogoutListener(player, "Knight's training") {
                removeAttributes(player, GameAttributes.PRAYER_LOCK,
                    KnightWavesUtils.KW_SPAWN,
                    KnightWavesUtils.KW_TIER,
                    KnightWavesUtils.KW_BEGIN
                )
            }
            GameWorld.Pulser.submit(object : Pulse(1) {
                override fun pulse(): Boolean {
                    /*
                     * Start waves.
                     */
                    KnightWavesNPC(player.getAttribute(KnightWavesUtils.KW_TIER, 6177), Location.create(2758, 3508, 2), player).init()
                    sendMessageWithDelay(player, "Remember, only melee combat is allowed in here.", 1)
                    return true
                }
            })
            return@on true
        }

        on(NPCs.MERLIN_213, IntType.NPC, "talk-to") { player, _ ->
            /*
             * After receive reward, this is authentic return for talk-to interaction.
             */
            sendMessage(player, "Nothing interesting happens.")
            return@on true
        }
    }
}