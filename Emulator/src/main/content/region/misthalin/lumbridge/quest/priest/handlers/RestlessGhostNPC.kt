package content.region.misthalin.lumbridge.quest.priest.handlers

import cfg.consts.NPCs
import core.api.isQuestComplete
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.GameWorld.ticks
import core.game.world.map.Location

/**
 * Restless ghost NPC.
 */
class RestlessGhostNPC : AbstractNPC {
    constructor() : super(0, null)

    internal constructor(id: Int, location: Location) : super(id, location, false)

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return RestlessGhostNPC(id, location)
    }

    override fun init() {
        super.init()
        properties.combatPulse.style = CombatStyle.MELEE
    }

    override fun tick() {
        super.tick()
        val pl = getAttribute<Player>("player", null)
        if (pl != null) {
            if (getAttribute("dead", false) || !getLocation().withinDistance(pl.location, 16)) {
                clear()
            }
        }
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        val player = (entity as Player)
        val pl = getAttribute<Player>("player", null)
        return pl != null && pl === player
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        removeAttribute("player")
    }

    override fun isHidden(player: Player): Boolean {
        val pl = getAttribute<Player>("player", null)
        if (this.respawnTick > ticks) {
            return true
        }
        return isQuestComplete(player, "The Restless Ghost") || (pl != null && player !== pl)
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.SKELETON_459, NPCs.RESTLESS_GHOST_457)
    }

}