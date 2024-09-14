package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.MultiSwingHandler
import core.game.node.entity.combat.equipment.SwitchAttack
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.player.Player
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction

/**
 * Iron titan npc.
 */
@Initializable
class IronTitanNPC @JvmOverloads constructor(owner: Player? = null, id: Int = 7375) :
    Familiar(owner, id, 6000, 12822, 12, WeaponInterface.STYLE_DEFENSIVE) {
    private var specialMove = false
    /**
     * Instantiates a new Iron titan npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    /**
     * Instantiates a new Iron titan npc.
     */
    init {
        super.combatHandler = object : MultiSwingHandler(true, *ATTACKS) {
            override fun swing(entity: Entity?, victim: Entity?, s: BattleState?): Int {
                val ticks = super.swing(entity, victim, s)
                if (specialMove) {
                    val states = arrayOfNulls<BattleState>(3)
                    for (i in 1..2) {
                        states[i] = BattleState(entity, victim)
                        val state = states[i]
                        var hit = 0
                        if (isAccurateImpact(entity, victim)) {
                            val max = calculateHit(entity, victim, 1.0)
                            state!!.maximumHit = max
                            hit = RandomFunction.random(max)
                            state.estimatedHit = hit
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
        return IronTitanNPC(owner, id)
    }

    override fun specialMove(special: FamiliarSpecial): Boolean {
        if (specialMove) {
            owner.packetDispatch.sendMessage("Your familiar is already charging its attack.")
            return false
        }
        specialMove = true
        visualize(Animation.create(8183), Graphic.create(1450))
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(7375, 7376)
    }

    companion object {
        private val ATTACKS = arrayOf(SwitchAttack(CombatStyle.MELEE.swingHandler, Animation.create(8183)))
    }
}
