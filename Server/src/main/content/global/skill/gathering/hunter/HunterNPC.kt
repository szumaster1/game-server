package content.global.skill.gathering.hunter

import content.global.skill.gathering.hunter.Traps.Companion.getNode
import core.api.consts.NPCs
import core.api.getPathableRandomLocalCoordinate
import core.api.sendGraphics
import core.api.teleport
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.node.entity.player.link.TeleportManager
import core.game.world.GameWorld
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Hunter NPC.
 */
class HunterNPC(id: Int, location: Location?, val trap: Traps?, val type: TrapNode?) : AbstractNPC(id, location) {

    constructor() : this(0, null, null, null) {
        this.isWalks = true
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        val data = getNode(id)
        return HunterNPC(id, location, data!![0] as Traps, data[1] as TrapNode)
    }

    override fun updateLocation(last: Location) {
        val wrapper = trap!!.getByHook(getLocation())
        wrapper?.type?.catchNpc(wrapper, this)
    }

    override fun getMovementDestination(): Location {
        if (trap!!.getHooks().isEmpty() || RandomFunction.random(170) > 5) {
            return super.getMovementDestination()
        }
        val hook = trap.getHooks()[RandomFunction.random(trap.getHooks().size)]
        if (!type!!.canCatch(hook.wrapper, this)) {
            return super.getMovementDestination()
        }
        val destination = hook.chanceLocation
        return if (destination != null && destination.getDistance(getLocation()) <= 24) destination else super.getMovementDestination()
    }

    override fun handleDrops(p: Player, killer: Entity) {
        val ticks = getAttribute("hunter", 0)
        if (ticks < GameWorld.ticks) {
            super.handleDrops(p, killer)
        }
    }

    override fun getIds(): IntArray {
        val ids: MutableList<Int> = ArrayList(10)
        for (t in Traps.values()) {
            for (node in t.nodes) {
                for (id in node.npcIds) {
                    ids.add(id)
                }
            }
        }
        val array = IntArray(ids.size)
        for (i in array.indices) {
            array[i] = ids[i]
        }
        return array
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)

        if (this.id == NPCs.IMP_708 || (this.id == NPCs.IMP_709) || (this.id == NPCs.IMP_1531)) {
            if (RandomFunction.roll(IMP_TELEPORT_CHANCE_ON_HIT)) {
                randomLocAndTeleport
            }
        }
    }

    override fun tick() {
        super.tick()

        if (this.id == NPCs.IMP_708 || (this.id == NPCs.IMP_709) || (this.id == NPCs.IMP_1531)) {
            if (RandomFunction.roll(IMP_TELEPORT_CHANCE_ON_TICK)) {
                randomLocAndTeleport
            }
        }
    }

    private val randomLocAndTeleport: Unit
        get() {
            val teleportLocation = getPathableRandomLocalCoordinate(this, walkRadius, properties.spawnLocation, 3)

            if (getLocation() !== teleportLocation) {
                sendGraphics(1119, getLocation())
                teleport(this, teleportLocation, TeleportManager.TeleportType.INSTANT)
            }
        }

    companion object {
        private const val IMP_TELEPORT_CHANCE_ON_HIT = 10 // 1/10
        private const val IMP_TELEPORT_CHANCE_ON_TICK = 1000 // 1/75
    }
}
