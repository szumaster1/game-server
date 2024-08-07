package content.minigame.pestcontrol.npc

import content.minigame.pestcontrol.PestControlSession
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.world.map.*
import core.game.world.update.flag.context.Animation
import core.tools.RandomFunction

/**
 * Pest Control portal NPC.
 */
class PCPortalNPC(id: Int = 6142, l: Location? = null) : AbstractNPC(id, l) {

    var updateLifepoints = true
    private var session: PestControlSession? = null
    private val spinners = arrayOfNulls<PCSpinnerNPC>(3)
    private val brawlers = arrayOfNulls<NPC>(2)
    override fun init() {
        super.setWalks(false)
        super.setRespawn(false)
        super.getProperties().isRetaliating = false
        super.init()
        properties.attackAnimation = Animation.create(-1)
        properties.defenceAnimation = Animation.create(-1)
        properties.deathAnimation = Animation.create(-1)
        session = getExtension(PestControlSession::class.java)
    }

    override fun tick() {
        super.tick()
        if (session != null) {
            val plane = viewport.currentPlane
            if (plane != null && session!!.ticks % 35 - plane.players.size == 0 && plane.npcs.size < 100) {
                spawnNPCs()
            }
            if (updateLifepoints && session!!.ticks % 10 == 0) {
                val color = if (getSkills().lifepoints > 50) "<col=00FF00>" else "<col=FF0000>"
                session!!.sendString(color + getSkills().lifepoints, 13 + portalIndex)
                updateLifepoints = false
            }
        }
    }

    override fun shouldPreventStacking(mover: Entity): Boolean {
        return true
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        updateLifepoints = true
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
     * Spawn NPCs
     *
     */
    fun spawnNPCs() {
        val dir = Direction.getLogicalDirection(getLocation(), session!!.squire.location)
        val index = difficultyIndex
        val r = RandomFunction.RANDOM
        var amount = index + 1
        if (viewport.currentPlane != null) {
            amount += viewport.currentPlane.players.size / 10
        }
        if (difficultyIndex == 0) {
            amount += (difficultyIndex + 1) * 3 / 2
        }
        for (i in 0 until amount) {
            var ids = SHIFTERS[index]
            when (r.nextInt(7)) {
                0 -> ids = SPLATTERS[index]
                1 -> ids = SHIFTERS[index]
                2 -> ids = RAVAGERS[index]
                3 -> {
                    var spawn = false
                    for (npc in spinners) {
                        if (npc == null || !npc.isActive) {
                            spawn = true
                            break
                        }
                    }
                    ids = if (spawn) {
                        SPINNERS[index]
                    } else {
                        TORCHERS[index]
                    }
                }

                4 -> ids = TORCHERS[index]
                5 -> ids = DEFILERS[index]
                6 -> {
                    var spawn = false
                    for (npc in brawlers) {
                        if (npc == null || !npc.isActive) {
                            spawn = true
                            break
                        }
                    }
                    ids = if (spawn) {
                        BRAWLERS[index]
                    } else {
                        DEFILERS[index]
                    }
                }
            }
            var x = dir.stepX * 3
            var y = dir.stepY * 3
            if (x == 0) {
                x = i
            } else {
                y = i
            }
            val n = session!!.addNPC(create(ids[r.nextInt(ids.size)], getLocation().transform(x, y, 0)))
            if (ids == RAVAGERS[index]) {
                (n as PCRavagerNPC).portalIndex = portalIndex
            } else if (ids == SPINNERS[index]) {
                for (j in spinners.indices) {
                    if (spinners[j] == null || !spinners[j]!!.isActive) {
                        spinners[j] = (n as PCSpinnerNPC).setPortalIndex(portalIndex)
                        break
                    }
                }
            }
            n.isWalks = true
            n.isRespawn = false
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return PCPortalNPC(id, location)
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        if (session != null) {
            var value = 0
            var endSession = true
            for (i in 0..3) {
                if (!session!!.portals[i].isActive) {
                    value = value or (1 shl i)
                } else {
                    endSession = false
                }
            }
            if (value > 0) {
                session!!.sendConfig(value shl 28)
                if (endSession) {
                    session!!.activity.end(session, true)
                    return
                }
            }
            for (npc in spinners) {
                if (npc != null && npc.isActive) {
                    npc.explode()
                }
            }
            session!!.sendString("<col=FF0000>0", 13 + portalIndex)
            session!!.squire.getSkills().heal(50)
            (session!!.squire as PCSquireNPC).FlagInterfaceUpdate()
        }
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            6142,
            6143,
            6144,
            6145,
            6146,
            6147,
            6148,
            6149,
            6150,
            6151,
            6152,
            6153,
            6154,
            6155,
            6156,
            6157,
            7551,
            7552,
            7553,
            7554,
            7555,
            7556,
            7557,
            7558
        )
    }

    val difficultyIndex: Int
        get() {
            if (id > 7550) {
                return 2
            }
            return if (id > 6149) {
                1
            } else 0
        }
    val portalIndex: Int
        get() {
            if (id > 7550) {
                return (id - 7551) % 4
            }
            return if (id > 6149) {
                (id - 6150) % 4
            } else (id - 6142) % 4
        }

    companion object {
        private val SPLATTERS =
            arrayOf(intArrayOf(3727, 3728, 3729), intArrayOf(3728, 3729, 3730), intArrayOf(3729, 3730, 3731))
        private val SHIFTERS = arrayOf(
            intArrayOf(3732, 3733, 3734, 3735, 3736, 3737),
            intArrayOf(3734, 3735, 3736, 3737, 3738, 3739),
            intArrayOf(3736, 3737, 3738, 3739, 3740, 3741)
        )
        private val RAVAGERS =
            arrayOf(intArrayOf(3742, 3743, 3744), intArrayOf(3743, 3744, 3745), intArrayOf(3744, 3745, 3746))
        private val SPINNERS =
            arrayOf(intArrayOf(3747, 3748, 3749), intArrayOf(3748, 3749, 3750), intArrayOf(3749, 3750, 3751))
        private val TORCHERS = arrayOf(
            intArrayOf(3752, 3753, 3754, 3755, 3756, 3757),
            intArrayOf(3754, 3755, 3756, 3757, 3758, 3759),
            intArrayOf(3756, 3757, 3758, 3759, 3760, 3761)
        )
        private val DEFILERS = arrayOf(
            intArrayOf(3762, 3763, 3764, 3765, 3766, 3767),
            intArrayOf(3764, 3765, 3766, 3767, 3768, 3769),
            intArrayOf(3766, 3767, 3768, 3769, 3770, 3771)
        )
        private val BRAWLERS =
            arrayOf(intArrayOf(3772, 3773, 3774), intArrayOf(3773, 3774, 3775), intArrayOf(3774, 3775, 3776))


        val portalIds = arrayOf(
            6142,
            6143,
            6144,
            6145,
            6146,
            6147,
            6148,
            6149,
            6150,
            6151,
            6152,
            6153,
            6154,
            6155,
            6156,
            6157,
            7551,
            7552,
            7553,
            7554,
            7555,
            7556,
            7557,
            7558
        )
    }
}
