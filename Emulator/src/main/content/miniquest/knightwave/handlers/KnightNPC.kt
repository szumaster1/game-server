package content.miniquest.knightwave.handlers

import cfg.consts.NPCs
import core.api.poofClear
import core.api.teleport
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.equipment.Weapon
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.plugin.Initializable

/**
 * Represents the Knights of the Round Table that appear in the Knight Waves training ground activity.
 */
@Initializable
class KnightNPC : AbstractNPC {
    var type: KnightType? = null
    private var commenced = false
    var player: Player? = null
    private var timer = 0

    constructor() : super(0, null)

    internal constructor(id: Int, location: Location?, player: Player?) : super(id, location) {
        this.isWalks = true
        this.isRespawn = false
        this.type = KnightType.forId(id)
        this.player = player
    }

    override fun handleTickActions() {
        super.handleTickActions()
        player?.let {
            if (!it.isActive || !getLocalPlayers(this).contains(it)) {
                it.removeAttribute(KWUtils.KW_SPAWN)
                clear()
            } else if (!properties.combatPulse.isAttacking) {
                properties.combatPulse.attack(it)
            }
        }
        if (timer++ > 500) poofClear(this)
    }

    override fun finalizeDeath(killer: Entity?) {
        if (killer == player) {
            type?.transform(this, player)
            this.isInvisible = true
            timer = 0 // Reset the timer each wave.
        } else {
            super.finalizeDeath(killer)
        }
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        return player == entity
    }

    override fun canSelectTarget(target: Entity): Boolean {
        return target is Player && target == player
    }

    override fun checkImpact(state: BattleState) {
        super.checkImpact(state)
        if (state.attacker is Player) {
            when (state.style) {
                CombatStyle.MELEE -> {
                    state.neutralizeHits()
                    state.estimatedHit = state.maximumHit
                }

                CombatStyle.RANGE, CombatStyle.MAGIC -> {
                    val specialAttack = player!!.getExtension<WeaponInterface>(WeaponInterface::class.java)
                    if (specialAttack.isSpecialBar && state.style != CombatStyle.MELEE) {
                        if (state.estimatedHit > -1) state.estimatedHit = 0
                    }
                    if (state.secondaryHit > -1) state.secondaryHit = 0
                }

                else -> {
                    if (state.weapon.type != Weapon.WeaponType.DEFAULT) {
                        if (state.estimatedHit > -1) state.estimatedHit = 0
                        if (state.secondaryHit > -1) state.secondaryHit = 0
                    }
                }
            }
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return KnightNPC(id, location, null)
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.SIR_BEDIVERE_6177,
            NPCs.SIR_PELLEAS_6176,
            NPCs.SIR_TRISTRAM_6175,
            NPCs.SIR_PALOMEDES_1883,
            NPCs.SIR_LUCAN_6173,
            NPCs.SIR_GAWAIN_6172,
            NPCs.SIR_KAY_6171,
            NPCs.SIR_LANCELOT_6170
        )
    }

    fun setCommenced(commenced: Boolean) {
        this.commenced = commenced
    }

    enum class KnightType(val id: Int) {
        I(6177), II(6176), III(6175), IV(1883), V(6173), VI(6172), VII(6171), VIII(6170);

        fun transform(npc: KnightNPC, player: Player?) {
            val newType = next()
            npc.lock()
            npc.pulseManager.clear()
            npc.walkingQueue.reset()
            player?.setAttribute(KWUtils.KW_TIER, this.id)
            Pulser.submit(object : Pulse(10, npc, player) {
                private var counter = 0

                override fun pulse(): Boolean {
                    return when (++counter) {
                        1 -> {
                            npc.unlock()
                            npc.animator.reset()
                            npc.fullRestore()
                            npc.type = newType
                            npc.transform(newType!!.id)
                            npc.getShownNPC(player)
                            npc.impactHandler.disabledTicks = 1
                            if (newType != VIII) {
                                npc.properties.combatPulse.attack(player)
                            } else {
                                player?.setAttribute(KWUtils.KW_KC, true)
                                teleport(player!!, Location.create(2750, 3507, 2))
                                MerlinNPC.spawnMerlin(player)
                                npc.clear()
                            }
                            player?.unlock()
                            true
                        }

                        else -> false
                    }
                }
            })
        }

        fun next(): KnightType? {
            return if (ordinal + 1 < knightTypes.size) knightTypes[ordinal + 1] else knightTypes[ordinal]
        }

        companion object {
            fun forId(id: Int): KnightType? {
                return values().find { it.id == id }
            }

            private val knightTypes = values()
        }
    }
}
