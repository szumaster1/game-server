package content.global.skill.slayer.npc

import content.global.skill.slayer.SlayerEquipmentFlags
import content.global.skill.slayer.Tasks
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.MagicSwingHandler
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.map.Location
import core.plugin.Initializable

/**
 * Represents the Aberrant spectre NPC.
 */
@Initializable
class AberrantSpectreNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return AberrantSpectreNPC(id, location)
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        if (state.attacker is Player) {
            val player = state.attacker as Player
            if (!SlayerEquipmentFlags.hasNosePeg(player)) {
                state.neutralizeHits()
            }
        }
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return COMBAT_HANDLER
    }

    override fun getIds(): IntArray {
        return Tasks.ABERRANT_SPECTRES.npcs
    }

    companion object {
        private val SKILLS = intArrayOf(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE, Skills.RANGE, Skills.MAGIC, Skills.PRAYER, Skills.AGILITY)

        private val COMBAT_HANDLER: MagicSwingHandler = object : MagicSwingHandler() {
            override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
                if (victim is Player) {
                    val player = victim
                    if (!SlayerEquipmentFlags.hasNosePeg(player)) {
                        for (skill in SKILLS) {
                            val drain = (player.getSkills().getStaticLevel(skill) * 0.5).toInt()
                            player.getSkills().updateLevel(skill, -drain, 0)
                        }
                    }
                }
                super.impact(entity, victim, state)
            }
        }
    }
}
