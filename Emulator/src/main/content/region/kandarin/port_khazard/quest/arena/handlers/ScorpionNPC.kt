package content.region.kandarin.port_khazard.quest.arena.handlers

import core.api.*
import org.rs.consts.NPCs
import content.region.kandarin.port_khazard.quest.arena.dialogue.GeneralDialogueFile
import core.game.node.entity.Entity
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Scorpion NPC.
 */
@Initializable
class ScorpionNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return ScorpionNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.KHAZARD_SCORPION_271)
    }

    companion object {

        fun spawnScorpion(player: Player) {
            val scorpion = ScorpionNPC(NPCs.KHAZARD_SCORPION_271)
            scorpion.location = location(2604, 3159, 0)
            scorpion.isWalks = true
            scorpion.isAggressive = true
            scorpion.isActive = false

            if (scorpion.asNpc() != null && scorpion.isActive && getAttribute(player, "spawn-scorpion", false)) {
                scorpion.properties.teleportLocation = scorpion.properties.spawnLocation
            }
            scorpion.isActive = true
            GameWorld.Pulser.submit(object : Pulse(2, scorpion) {
                override fun pulse(): Boolean {
                    scorpion.init()
                    scorpion.attack(player)
                    return true
                }
            })
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            val quest = "Fight Arena"
            if (getQuestStage(killer, quest) == 88) {
                setQuestStage(killer, "Fight Arena", 89)
            }
            removeAttribute(killer, "spawn-scorpion")
            openDialogue(killer, GeneralDialogueFile())
        }
        clear()
        super.finalizeDeath(killer)
    }
}