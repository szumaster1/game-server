package content.region.fremennik.jatizso

import core.api.*
import org.rs.consts.NPCs
import org.rs.consts.Scenery
import org.rs.consts.Sounds
import content.region.fremennik.jatizso.dialogue.LeftieRightieDialogue
import core.game.dialogue.FacialExpression
import core.game.interaction.IntType
import core.game.interaction.InteractionListener
import core.game.node.entity.npc.NPC
import core.game.system.task.Pulse
import core.game.world.map.Direction
import core.game.world.map.Location
import core.game.world.map.zone.ZoneBorders

class JatizsoListener : InteractionListener {

    override fun defineListeners() {

        /*
         * This listener handles the opening of closed gates.
         */

        on(GATES_CLOSED, IntType.SCENERY, "open") { player, node ->
            if (NORTH_GATE_ZONE.insideBorder(player)) {
                if (node.id == GATES_CLOSED.first()) {
                    val other = getScenery(node.location.transform(1, 0, 0)) ?: return@on true
                    replaceScenery(node.asScenery(), node.id + 1, -1, Direction.EAST)
                    replaceScenery(other.asScenery(), other.id - 1, -1, Direction.NORTH_EAST)
                } else {
                    val other = getScenery(node.location.transform(-1, 0, 0)) ?: return@on true
                    replaceScenery(node.asScenery(), node.id + 1, -1, Direction.NORTH_EAST)
                    replaceScenery(other.asScenery(), other.id, -1, Direction.EAST)
                }
            } else if (WEST_GATE_ZONE.insideBorder(player)) {
                if (node.id == GATES_CLOSED.first()) {
                    val other = getScenery(node.location.transform(0, 1, 0)) ?: return@on true
                    replaceScenery(node.asScenery(), node.id + 1, -1, Direction.WEST)
                    replaceScenery(other.asScenery(), other.id + 1, -1, Direction.NORTH)
                } else {
                    val other = getScenery(node.location.transform(0, -1, 0)) ?: return@on true
                    replaceScenery(node.asScenery(), node.id + 1, -1, Direction.NORTH)
                    replaceScenery(other.asScenery(), other.id + 1, -1, Direction.WEST)
                }
            } else if (SOUTH_GAE_ZONE.insideBorder(player)) {
                if (node.id == GATES_CLOSED.first()) {
                    val other = getScenery(node.location.transform(-1, 0, 0)) ?: return@on true
                    replaceScenery(node.asScenery(), node.id + 1, -1, Direction.SOUTH)
                    replaceScenery(other.asScenery(), other.id - 1, -1, Direction.EAST)
                } else {
                    val other = getScenery(node.location.transform(1, 0, 0)) ?: return@on true
                    replaceScenery(node.asScenery(), node.id + 1, -1, Direction.EAST)
                    replaceScenery(other.asScenery(), other.id, -1, Direction.SOUTH)
                }
            }
            playAudio(player, Sounds.NICEDOOR_OPEN_81)
            return@on true
        }

        /*
         * This listener handles the behavior of tower guards.
         */

        on(TOWER_GUARDS, IntType.NPC, "watch-shouting") { player, _ ->
            val local = findLocalNPC(player, NPCs.GUARD_5489)
            lock(player, 200)
            face(local!!, Location.create(2371, 3801, 2))
            local.asNpc().isRespawn = false
            submitIndividualPulse(player, object : Pulse(4) {
                var id = NPCs.GUARD_5489
                var counter = 0
                val other = findLocalNPC(player, getOther(NPCs.GUARD_5489))

                override fun start() {
                    other?.isRespawn = false
                }

                override fun pulse(): Boolean {
                    val npc = findLocalNPC(player, id) ?: return false
                    val index = when (id) {
                        NPCs.GUARD_5489 -> 0
                        else -> 1
                    }
                    if (index == 1 && counter == 5) return true
                    sendChat(npc, LINES[index][counter])
                    if (npc.id != NPCs.GUARD_5489) counter++
                    id = getOther(id)
                    return false
                }

                override fun stop() {
                    unlock(player)
                    other?.isRespawn = true
                    local.asNpc().isRespawn = true
                    super.stop()
                }

                fun getOther(npc: Int): Int {
                    return when (npc) {
                        NPCs.GUARD_5489 -> NPCs.GUARD_5490
                        NPCs.GUARD_5490 -> NPCs.GUARD_5489
                        else -> NPCs.GUARD_5489
                    }
                }
            })
            return@on true
        }

        /*
         * This listener handles the ringing of the bell.
         */

        on(BELL, IntType.SCENERY, "ring-bell") { player, _ ->
            playAudio(player, Sounds.STEEL_15)
            sendMessage(player, "You ring the warning bell, but everyone ignores it!")
            return@on true
        }

        /*
         * This listener handles the interaction with guards.
         */

        on(GUARDS, IntType.NPC, "talk-to") { player, _ ->
            player.dialogueInterpreter.open(LeftieRightieDialogue(), NPC(NPCs.GUARD_5491))
            return@on true
        }

        /*
         * This listener handles the opening of the king's chest.
         */

        on(KING_CHEST, IntType.SCENERY, "open") { player, _ ->
            sendPlayerDialogue(player, "I probably shouldn't mess with that.", FacialExpression.HALF_THINKING)
            return@on true
        }

        /*
         * This listener sets the destination for the Magnus NPC.
         */

        setDest(IntType.NPC, MAGNUS_NPC) { _, _ ->
            return@setDest Location.create(2416, 3801, 0)
        }

        /*
         * This section handles the climbing of ladders in the towers around the city walls.
         */

        addClimbDest(Location.create(2388, 3804, 0), Location.create(2387, 3804, 2))
        addClimbDest(Location.create(2388, 3804, 2), Location.create(2387, 3804, 0))
        addClimbDest(Location.create(2388, 3793, 0), Location.create(2387, 3793, 2))
        addClimbDest(Location.create(2388, 3793, 2), Location.create(2387, 3793, 0))
        addClimbDest(Location.create(2410, 3823, 0), Location.create(2410, 3824, 2))
        addClimbDest(Location.create(2410, 3823, 2), Location.create(2410, 3824, 0))
        addClimbDest(Location.create(2421, 3823, 0), Location.create(2421, 3824, 2))
        addClimbDest(Location.create(2421, 3823, 2), Location.create(2421, 3824, 0))
    }

    companion object {
        const val BELL = Scenery.BELL_21394
        const val MAGNUS_NPC = NPCs.MAGNUS_GRAM_5488
        val GATES_CLOSED = intArrayOf(21403, 21405)
        val NORTH_GATE_ZONE = ZoneBorders(2414, 3822, 2417, 3825)
        val WEST_GATE_ZONE = ZoneBorders(2386, 3797, 2390, 3801)
        val SOUTH_GAE_ZONE = ZoneBorders(2411, 3795, 2414, 3799)
        val TOWER_GUARDS = intArrayOf(5490, 5489)
        val GUARDS = intArrayOf(5491, 5492)
        val KING_CHEST = intArrayOf(21299, 21300)

        val LINES = arrayOf(
            arrayOf("YOU WOULDN'T KNOW HOW TO FIGHT A TROLL IF IT CAME UP AND BIT YER ARM OFF", "YAK LOVERS", "CALL THAT A TOWN? I'VE SEEN BETTER HAMLETS!", "AND YOUR FATHER SMELLED OF WINTERBERRIES!", "WOODEN BRIDGES ARE RUBBISH!", "OUR KING'S BETTER THAN YOUR BURGHER!"),
            arrayOf("YOU WOULDN'T KNOW HOW TO SHAVE A YAK IF YOUR LIFE DEPENDED ON IT", "MISERABLE LOSERS", "YOUR MOTHER WAS A HAMSTER!", "AT LEAST WE HAVE SOMETHING OTHER THAN SMELLY FISH!", "AT LEAST WE CAN COOK!")
        )
    }
}