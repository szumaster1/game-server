package content.region.kandarin.handlers.miniquest.knightwave

import cfg.consts.NPCs
import core.api.getAttribute
import core.api.openDialogue
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location

/**
 * Represents the Knights of the Round Table NPC.
 */
class KnightNPC(id: Int = 0, location: Location? = null, session: TrainingGroundSession? = null) :
    AbstractNPC(id, location) {

    private val session: TrainingGroundSession? = session
    private val type: KnightType? = KnightType.forId(id)
    private var isSpawned: Boolean = false

    init {
        isWalks = true
        isRespawn = false
    }

    override fun init() {
        super.init()
        session?.let {
            if (it.player.location.regionId == 11062) {
                Pulser.submit(Transform(it.player, this))
            } else {
                it.close()
            }
        }
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return KnightNPC(id, location)
    }

    override fun handleTickActions() {
        super.handleTickActions()
        session?.takeIf { it.player.isActive }?.let {
            if (isSpawned && !properties.combatPulse.isAttacking) {
                properties.combatPulse.attack(it.player)
            }
        } ?: clear()
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        return style == CombatStyle.MELEE && session?.player == entity
    }

    override fun canSelectTarget(target: Entity): Boolean {
        return target is Player && target === session?.player
    }

    override fun checkImpact(state: BattleState) {
        if (state.attacker is Player && state.victim is NPC && state.style == CombatStyle.MELEE) {
            state.estimatedHit = state.maximumHit
        } else {
            state.neutralizeHits()
        }
    }

    class Transform(private val player: Player?, private val knight: KnightNPC) : Pulse() {
        private var counter = 0

        override fun pulse(): Boolean {
            return when (counter++) {
                3 -> {
                    knight.init()
                    false
                }

                4 -> {
                    knight.attack(player)
                    true
                }

                else -> false
            }
        }
    }

    override fun startDeath(killer: Entity) {
        if (killer === session?.player) {
            val wave = getAttribute(killer, KnightWave.KW_TIER, KnightWave.tier)
            if (wave < 8) {
                killer.incrementAttribute(KnightWave.KW_TIER)
                if (wave == 8) {
                    session.close()
                    teleport(Location.create(2750, 3507, 2))
                    MerlinNPC.spawnMerlin(player = killer).also {
                        openDialogue(player = killer, MerlinDialogue())
                    }

                }
            }
            Pulser.submit(Transform(session.player, this))
        }
        clear()
        super.startDeath(killer)
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

    enum class KnightType(val npc: Int, var wave: Int) {
        /*
         * First wave.
         */
        I(NPCs.SIR_BEDIVERE_6177, 0),

        /*
         * Second wave.
         */
        II(NPCs.SIR_PELLEAS_6176, 1),

        /*
         * Third wave.
         */
        III(NPCs.SIR_TRISTRAM_6175, 2),

        /*
         * Four wave.
         */
        IV(NPCs.SIR_PALOMEDES_1883, 3),

        /*
         * Five wave.
         */
        V(NPCs.SIR_LUCAN_6173, 4),

        /*
         * Six wave.
         */
        VI(NPCs.SIR_GAWAIN_6172, 5),

        /*
         * Seven wave.
         */
        VII(NPCs.SIR_KAY_6171, 6),

        /*
         * Eight wave.
         */
        VIII(NPCs.SIR_LANCELOT_6170, 7);


        companion object {
            @JvmStatic
            fun forId(id: Int): KnightType? {
                return values().find { it.npc == id }
            }
        }
    }
}

