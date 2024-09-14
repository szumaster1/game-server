package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Steel titan npc.
 */
@Initializable
class SteelTitanNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7343) :
    Familiar(owner, id, 6400, 12790, 12, WeaponInterface.STYLE_RANGE_ACCURATE) {
    private var specialMove = false
    /**
     * Instantiates a new Steel titan npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    /**
     * Instantiates a new Steel titan npc.
     */
    init {
        super.combatHandler = object : MultiSwingHandler(true, *ATTACKS) {
            override fun swing(entity: Entity?, victim: Entity?, s: BattleState?): Int {
                val ticks = super.swing(entity, victim, s)
                if (specialMove) {
                    val states = arrayOfNulls<BattleState>(4)
                    for (i in 1..3) {
                        states[i] = BattleState(entity, victim)
                        val state = states[i]
                        var hit = 0
                        if (isAccurateImpact(entity, victim)) {
                            val max = calculateHit(entity, victim, 1.0)
                            state!!.maximumHit = max
                            hit = RandomFunction.random(max)
                        }
                        state!!.estimatedHit = hit
                        state.style = current.style
                    }
                    states[0] = s
                    s!!.targets = states
                    specialMove = false
                }
                return ticks
            }
        }
    }

    override fun construct(owner: Player, id: Int): Familiar {
        return SteelTitanNPC(owner, id)
    }

    override fun getIds(): IntArray {
        return intArrayOf(7343, 7344)
    }

    public override fun specialMove(special: FamiliarSpecial): Boolean {
        if (specialMove) {
            owner.packetDispatch.sendMessage("Your familiar is already charging its attack.")
            return false
        }
        specialMove = true
        visualize(Animation.create(8183), Graphic.create(1449))
        return true
    }

    companion object {
        private val ATTACKS = arrayOf(
            SwitchAttack(CombatStyle.RANGE.swingHandler, Animation.create(8190), null, null, Projectile.create(null, null, 1445, 60, 36, 41, 46)),
            SwitchAttack(CombatStyle.MAGIC.swingHandler, Animation.create(8190), null, null, Projectile.create(null, null, 1445, 60, 36, 41, 46)),
            SwitchAttack(CombatStyle.MELEE.swingHandler, Animation.create(8183))
        )
    }
}
