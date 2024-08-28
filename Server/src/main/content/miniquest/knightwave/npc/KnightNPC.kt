package content.miniquest.knightwave.npc

import cfg.consts.NPCs
import content.miniquest.knightwave.KnightWaves
import core.api.teleport
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.plugin.Initializable

/**
 * Knight of Round Table NPC.
 */
@Initializable
class KnightNPC : AbstractNPC {
    /**
     * Gets type.
     *
     * @return the type
     */
    /**
     * Sets type.
     *
     * @param type the type
     */
    var type: KnightType? = null
    private var commenced = false

    /**
     * The Player.
     */
    var player: Player? = null

    /**
     * Instantiates a new Knight npc.
     */
    constructor() : super(0, null)

    /**
     * Instantiates a new Knight npc.
     *
     * @param id       the id
     * @param location the location
     * @param player   the player
     */
    internal constructor(id: Int, location: Location?, player: Player?) : super(id, location) {
        this.isWalks = true
        this.isRespawn = false
        this.type = KnightType.forId(id)
        this.player = player
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (!player!!.isActive || !getLocalPlayers(this).contains(player)) {
            player!!.removeAttribute(KnightWaves.KW_SPAWN)
            clear()
        }
        if (!properties.combatPulse.isAttacking) {
            properties.combatPulse.attack(player)
        }
    }

    override fun startDeath(killer: Entity) {
        if (killer == player) {
            type!!.transform(this, player)
            return
        }
        super.startDeath(killer)
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        return player == entity
    }

    override fun canSelectTarget(target: Entity): Boolean {
        if (target is Player) {
            return target == this.player
        }
        return true
    }

    override fun checkImpact(state: BattleState) {
        if (state.style == CombatStyle.MELEE) {
            state.neutralizeHits()
            state.estimatedHit = state.maximumHit
        } else {
            if (state.estimatedHit > -1) {
                state.estimatedHit = 0
                return
            }
            if (state.secondaryHit > -1) {
                state.secondaryHit = 0
                return
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

    /**
     * Sets commenced.
     *
     * @param commenced the commenced
     */
    fun setCommenced(commenced: Boolean) {
        this.commenced = commenced
    }

    /**
     * The enum knight type.
     */
    enum class KnightType(
        /**
         * Gets id.
         *
         * @return the id
         */
        val id: Int
    ) {
        /*
         * First wave.
         */
        I(6177),

        /*
         * Second wave.
         */
        II(6176),

        /*
         * Third wave.
         */
        III(6175),

        /*
         * Four wave.
         */
        IV(1883),

        /*
         * Five wave.
         */
        V(6173),

        /*
         * Six wave.
         */
        VI(6172),

        /*
         * Seven wave.
         */
        VII(6171),

        /*
         * Eight wave.
         */
        VIII(6170);

        /**
         * Transform.
         *
         * @param npc    the npc
         * @param player the player
         */
        fun transform(npc: KnightNPC, player: Player?) {
            val newType = next()
            npc.lock()
            npc.pulseManager.clear()
            npc.walkingQueue.reset()
            player!!.setAttribute(KnightWaves.KW_TIER, this.id)
            Pulser.submit(object : Pulse(3, npc, player) {
                var counter: Int = 0

                override fun pulse(): Boolean {
                    when (++counter) {
                        1 -> {
                            npc.unlock()
                            npc.animator.reset()
                            npc.fullRestore()
                            npc.type = newType
                            npc.transform(newType!!.id)
                            npc.impactHandler.disabledTicks = 1
                            if (newType != VIII) {
                                npc.properties.combatPulse.attack(player)
                            }
                            if (newType == VIII) {
                                player.setAttribute(KnightWaves.KW_KC, true)
                                teleport(player, Location.create(2750, 3507, 2))
                                MerlinNPC.spawnMerlin(player)
                                npc.clear()
                                return false
                            }
                            player.unlock()
                            return true
                        }
                    }
                    return false
                }
            })
        }

        /**
         * Next knight type.
         *
         * @return the knight type
         */
        fun next(): KnightType? {
            return if (ordinal + 1 < knightTypes.size) {
                knightTypes[ordinal + 1]
            } else {
                knightTypes[ordinal]
            }
        }

        companion object {
            /**
             * For id knight type.
             *
             * @param id the id
             * @return the knight type
             */
            fun forId(id: Int): KnightType? {
                for (type in values()) {
                    if (type.id == id) {
                        return type
                    }
                }
                return null
            }

            private val knightTypes = values()
        }
    }
}
