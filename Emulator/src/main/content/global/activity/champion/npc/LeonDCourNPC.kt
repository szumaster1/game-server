package content.global.activity.champion.npc

import core.api.*
import org.rs.consts.Items
import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Leon d'Cour champion boss NPC for Champions challenge.
 */
@Initializable
class LeonDCourNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    var clearTime = 0

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return LeonDCourNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.LEON_DCOUR_3067)
    }


    override fun handleTickActions() {
        super.handleTickActions()
        if (clearTime++ > 288) poofClear(this)
    }

    companion object {
        fun spawnFinalChampion(player: Player) {
            val boss = LeonDCourNPC(NPCs.LEON_DCOUR_3067)
            boss.location = location(3170, 9758, 0)
            boss.isWalks = true
            boss.isAggressive = true
            boss.isActive = false

            if (boss.asNpc() != null && boss.isActive) {
                boss.properties.teleportLocation = boss.properties.spawnLocation
            }
            boss.isActive = true
            GameWorld.Pulser.submit(object : Pulse(0, boss) {
                override fun pulse(): Boolean {
                    boss.init()
                    boss.attack(player)
                    return true
                }
            })
        }
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            lock(killer, 2)
            runTask(killer, 1) {
                openInterface(killer, 63)
                sendString(killer, "Well done, you defeated the Human Champion!", 63, 2)
                killer.packetDispatch.sendItemZoomOnInterface(Items.CHAMPION_SCROLL_6808, 260, 63, 3)
                sendString(killer, "492 Slayer Xp", 63, 6)
                sendString(killer, "492 Hitpoint Xp", 63, 7)
            }
            rewardXP(killer, Skills.HITPOINTS, 492.0)
            rewardXP(killer, Skills.SLAYER, 492.0)
            removeAttribute("championsarena:start")
        }
        clear()
        super.finalizeDeath(killer)
    }
}