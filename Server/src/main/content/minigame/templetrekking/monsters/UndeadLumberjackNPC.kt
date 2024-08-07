package content.minigame.templetrekking.monsters
/*
import core.api.consts.NPCs
import core.api.toIntArray
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Swamp Snakes NPC
 * Combat levels: 30,35,40,45,60,70
 * Planks for obstacles, lumberjack clothes.
 */
val undeadLumberjackNPC = (5678..5699)
@Initializable
class UndeadLumberjackNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return UndeadLumberjackNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(/**/)
    }

    override fun handleTickActions() {
        super.handleTickActions()
    }

    companion object {

    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer is Player) {
            clear()
            super.finalizeDeath(killer)
        }
    }
}
*/
