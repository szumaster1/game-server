package content.minigame.pestcontrol.npc

import content.minigame.pestcontrol.PestControlSession
import core.api.applyPoison
import core.game.interaction.MovementPulse
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.ImpactHandler.HitsplatType
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.impl.PulseType
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction

/**
 * Represents the Pest Control spinner NPC.
 */
class PCSpinnerNPC : AbstractNPC {
    private var session: PestControlSession? = null
    private var portalIndex = 0

    constructor() : super(3747, null)

    constructor(id: Int, location: Location?) : super(id, location)

    override fun init() {
        super.init()
        super.walkRadius = 4
        session = getExtension(PestControlSession::class.java)
    }

    override fun tick() {
        super.tick()
        if (session != null && RandomFunction.RANDOM.nextInt(10) < 2) {
            val portal = session!!.portals[portalIndex]
            if (portal.isActive && portal.getSkills().lifepoints > 0) {
                if (portal.getSkills().lifepoints < portal.getSkills().maximumLifepoints) {
                    if (getLocation().withinDistance(portal.centerLocation, 5)) {
                        heal()
                    }
                    pulseManager.run(object : MovementPulse(this, portal) {
                        override fun pulse(): Boolean {
                            return true
                        }
                    }, PulseType.STANDARD)
                }
            }
        }
    }

    /**
     * Heal
     *
     */
    fun heal() {
        val portal = session!!.portals[portalIndex]
        if (!portal.isActive || portal.getSkills().lifepoints < 1) {
            return
        }
        faceTemporary(portal, 1)
        visualize(Animation(3911, Priority.HIGH), Graphic.create(658))
        portal.getSkills().heal(portal.getSkills().maximumLifepoints / 10)
        (portal as PCPortalNPC).updateLifepoints = true
    }

    /**
     * Explode
     *
     */
    fun explode() {
        animate(properties.deathAnimation)
        for (p in getLocalPlayers(this, 1)) {
            p.impactHandler.manualHit(this, 5, HitsplatType.POISON)
            applyPoison(p, this, 1)
        }
        Pulser.submit(object : Pulse(1, this) {
            override fun pulse(): Boolean {
                clear()
                return true
            }
        })
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

    /**
     * Set portal index
     *
     * @param portalIndex
     * @return
     */
    fun setPortalIndex(portalIndex: Int): PCSpinnerNPC {
        this.portalIndex = portalIndex
        return this
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return PCSpinnerNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(3747, 3748, 3749, 3750, 3751)
    }
}
