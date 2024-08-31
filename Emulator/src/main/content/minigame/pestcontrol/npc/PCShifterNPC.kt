package content.minigame.pestcontrol.npc

import content.minigame.pestcontrol.PestControlSession
import core.game.node.entity.Entity
import core.game.node.entity.combat.*
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.isTeleportPermitted
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.tools.RandomFunction
import java.util.*

/**
 * Represents the Pest Control shifter NPC.
 */
class PCShifterNPC : AbstractNPC {
    private var session: PestControlSession? = null

    constructor() : super(3732, null)

    constructor(id: Int, location: Location?) : super(id, location)

    override fun init() {
        super.setAggressive(true)
        super.init()
        super.getDefinition().combatDistance = 1
        super.walkRadius = 64
        properties.combatPulse.style = CombatStyle.MELEE
        session = getExtension(PestControlSession::class.java)
    }

    override fun tick() {
        super.tick()
        val pulse = properties.combatPulse
        if (session != null && !inCombat() && !pulse.isAttacking && RandomFunction.RANDOM.nextInt(50) < 2) {
            pulse.attack(session!!.squire)
        }
        if (pulse.isAttacking && !getLocation().withinDistance(pulse.getVictim()!!.location, 5)) {
            if (session == null || session!!.isActive) {
                teleport(session, this, getDestination(pulse.getVictim()))
            }
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

    private fun getDestination(victim: Entity?): Location? {
        val locations: MutableList<Location?> = ArrayList(20)
        val radius = 2
        for (x in -radius until radius + 1) {
            for (y in -radius until radius + 1) {
                if (x != 0 && y != 0) {
                    locations.add(victim!!.location.transform(x, y, 0))
                }
            }
        }
        Collections.shuffle(locations, RandomFunction.RANDOM)
        for (l in locations) {
            if (isTeleportPermitted(l!!)) {
                return l
            }
        }
        return null
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return PCShifterNPC(id, location)
    }

    override fun getIds(): IntArray {
        return intArrayOf(3732, 3733, 3734, 3735, 3736, 3737, 3738, 3739, 3740, 3741)
    }

    companion object {
        private val SWING_HANDLER: CombatSwingHandler = object : MeleeSwingHandler() {
            override fun canSwing(entity: Entity, victim: Entity): InteractionType? {
                // Allows for diagonal combat
                return CombatStyle.RANGE.swingHandler.canSwing(entity, victim)
            }
        }


        fun teleport(session: PestControlSession?, entity: Entity, destination: Location?) {
            if (destination == null || session != null && destination.regionId != session.region.id) {
                return
            }
            Graphic.send(Graphic.create(654), entity.location)
            entity.properties.teleportLocation = destination
            entity.walkingQueue.reset()
            entity.locks.lockMovement(2)
            entity.lock(3)
            Pulser.submit(object : Pulse(1, entity) {
                override fun pulse(): Boolean {
                    entity.animate(Animation.create(3904))
                    Graphic.send(Graphic.create(654), destination)
                    return true
                }
            })
        }
    }
}
