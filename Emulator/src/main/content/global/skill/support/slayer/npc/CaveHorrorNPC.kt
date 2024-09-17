package content.global.skill.support.slayer.npc

import content.global.skill.support.slayer.SlayerEquipmentFlags.hasWitchwoodIcon
import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.*
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Cave horror NPC.
 */
@Initializable
class CaveHorrorNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return CaveHorrorNPC(id, location)
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return COMBAT_HANDLER
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.CAVE_HORROR_4354,
            NPCs.CAVE_HORROR_4355,
            NPCs.CAVE_HORROR_4353,
            NPCs.CAVE_HORROR_4356,
            NPCs.CAVE_HORROR_4357
        )
    }

    companion object {
        private val COMBAT_HANDLER: MeleeSwingHandler = object : MeleeSwingHandler() {
            override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
                if (victim is Player) {
                    val player = victim
                    if (!hasWitchwood(player)) {
                        if (RandomFunction.random(10) < 5) {
                            state!!.estimatedHit = player.getSkills().lifepoints / 10
                        }
                    }
                }
                super.impact(entity, victim, state)
            }

            override fun isAttackable(entity: Entity, victim: Entity): InteractionType? {
                return CombatStyle.MELEE.swingHandler.isAttackable(entity, victim)
            }
        }

        fun hasWitchwood(player: Player?): Boolean {
            return hasWitchwoodIcon(player!!)
        }
    }
}
