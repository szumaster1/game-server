package content.minigame.pestcontrol.npc

import content.minigame.pestcontrol.PestControlSession
import core.game.node.entity.Entity
import core.game.node.entity.combat.*
import core.game.node.entity.combat.spell.CombatSpell
import core.game.node.entity.combat.spell.SpellType
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.SpellBookManager.SpellBook
import core.game.world.map.Location
import core.game.world.map.MapDistance
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Plugin

class PCTorcherNPC : AbstractNPC {
    private var session: PestControlSession? = null

    constructor() : super(3752, null)
    constructor(id: Int, location: Location?) : super(id, location)

    override fun init() {
        super.setAggressive(true)
        super.init()
        super.getDefinition().combatDistance = 10
        super.walkRadius = 64
        properties.combatPulse.style = CombatStyle.MAGIC
        super.getProperties().autocastSpell = SPELL
        session = getExtension(PestControlSession::class.java)
    }

    override fun tick() {
        super.tick()
        if (session != null && !inCombat() && !properties.combatPulse.isAttacking) {
            properties.combatPulse.attack(session!!.squire)
        }
    }

    override fun shouldPreventStacking(mover: Entity): Boolean {
        return mover is NPC
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        super.onImpact(entity, state)
        if (session != null && state != null && entity is Player) {
            var total = 0
            if (state.estimatedHit > 0) {
                total += state.estimatedHit
            }
            if (state.secondaryHit > 0) {
                total += state.secondaryHit
            }
            session!!.addZealGained(entity, total)
        }
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        return SWING_HANDLER
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return PCTorcherNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(3752, 3753, 3754, 3755, 3756, 3757, 3758, 3759, 3760, 3761)
    }

    companion object {
        private val SPELL = TorcherSpell()
        private val SWING_HANDLER: CombatSwingHandler = object : MagicSwingHandler() {
            override fun canSwing(entity: Entity, victim: Entity): InteractionType? {
                if (!isProjectileClipped(entity, victim, false)) {
                    return InteractionType.NO_INTERACT
                }
                if (victim.centerLocation.withinDistance(entity.centerLocation, 8) && isAttackable(
                        entity,
                        victim
                    ) !== InteractionType.NO_INTERACT
                ) {
                    if (victim.location.withinDistance(entity.location, MapDistance.RENDERING.distance / 2)) {
                        entity.walkingQueue.reset()
                    }
                    return InteractionType.STILL_INTERACT
                }
                return InteractionType.NO_INTERACT
            }
        }
    }
}

internal class TorcherSpell : CombatSpell(
    SpellType.STRIKE,
    SpellBook.MODERN,
    0,
    0.0,
    -1,
    -1,
    Animation(3882, Priority.HIGH),
    Graphic.create(646),
    Projectile.create(null as Entity?, null, 647, 40, 36, 52, 75, 15, 11),
    Graphic(648, 96)
) {
    override fun getMaximumImpact(entity: Entity, victim: Entity, state: BattleState): Int {
        return entity.properties.currentCombatLevel / 7
    }

    override fun newInstance(arg: SpellType): Plugin<SpellType> {
        return this
    }
}