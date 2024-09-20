package content.global.skill.slayer.npc

import content.global.skill.slayer.SlayerEquipmentFlags
import core.game.node.entity.Entity
import core.game.node.entity.combat.*
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.world.GameWorld.ticks
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.plugin.Initializable
import core.tools.RandomFunction
import org.rs.consts.Animations
import org.rs.consts.NPCs

/**
 * Represents the Banshee NPC.
 */
@Initializable
class BansheeNPC(id: Int = 0, location: Location? = null) : AbstractNPC(id, location) {

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return BansheeNPC(id, location)
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        super.onImpact(entity, state)
        if (state.attacker is Player) {
            val player = state.attacker as Player
            if (!hasEarMuffs(player)) {
                state.neutralizeHits()
            }
        }
        if (state.estimatedHit > 0 || state.secondaryHit > 0) {
            getSkills().heal(1)
        }
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return COMBAT_HANDLER
    }

    override fun getIds(): IntArray {
        return intArrayOf(NPCs.BANSHEE_1612)
    }

    companion object {
        private val SKILLS = intArrayOf(
            Skills.ATTACK,
            Skills.STRENGTH,
            Skills.DEFENCE,
            Skills.RANGE,
            Skills.MAGIC,
            Skills.PRAYER,
            Skills.AGILITY
        )

        private val COMBAT_HANDLER: MeleeSwingHandler = object : MeleeSwingHandler() {
            override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
                if (victim is Player) {
                    val player = victim
                    if (!hasEarMuffs(player)) {
                        if (RandomFunction.random(10) < 4 && player.properties.combatPulse.getNextAttack() <= ticks) {
                            player.walkingQueue.reset()
                            player.locks.lockMovement(3)
                            player.properties.combatPulse.setNextAttack(3)
                            player.animate(Animation(Animations.BEND_KNEES_AND_COVER_EARS_1572, Priority.HIGH))
                        }
                        for (skill in SKILLS) {
                            val drain = (player.getSkills().getStaticLevel(skill) * 0.5).toInt()
                            player.getSkills().updateLevel(skill, -drain, 0)
                        }
                        state!!.estimatedHit = 8
                    }
                }
                super.impact(entity, victim, state)
            }

            override fun isAttackable(entity: Entity, victim: Entity): InteractionType? {
                return CombatStyle.MAGIC.swingHandler.isAttackable(entity, victim)
            }
        }

        fun hasEarMuffs(player: Player?): Boolean {
            return SlayerEquipmentFlags.hasEarmuffs(player!!)
        }
    }
}
