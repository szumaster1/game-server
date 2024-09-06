package content.region.asgarnia.taverley.quest.ball.handlers

import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.game.world.map.RegionManager.getLocalPlayers
import core.plugin.Initializable

/**
 * Witch experiment npc.
 */
@Initializable
class WitchExperimentNPC : AbstractNPC {

    var type: ExperimentType? = null
    private var commenced = false

    var p: Player? = null

    constructor() : super(0, null)

    /**
     * Instantiates a new Witch experiment npc.
     *
     * @param id       the id
     * @param location the location
     * @param player   the player
     */
    internal constructor(id: Int, location: Location?, player: Player?) : super(id, location) {
        this.isWalks = true
        this.isRespawn = false
        type = ExperimentType.forId(id)
        p = player
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (!p!!.isActive || !getLocalPlayers(this).contains(p)) {
            p!!.removeAttribute("witchs-experiment:npc_spawned")
            clear()
        }
        if (!properties.combatPulse.isAttacking) {
            properties.combatPulse.attack(p)
        }
    }

    override fun startDeath(killer: Entity) {
        if (killer === p) {
            type!!.transform(this, p)
            return
        }
        super.startDeath(killer)
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        return p === entity
    }

    override fun canSelectTarget(target: Entity): Boolean {
        if (target is Player) {
            return target === this.p
        }
        return true
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return WitchExperimentNPC(id, location, null)
    }

    override fun getIds(): IntArray {
        return intArrayOf(897, 898, 899, 900)
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
     * The enum Experiment type.
     */
    enum class ExperimentType(
        /**
         * Gets id.
         *
         * @return the id
         */
        val id: Int, vararg message: String
    ) {
        /**
         * First experiment type.
         */
        FIRST(897, ""),

        /**
         * The Second.
         */
        SECOND(898, "The shapeshifter's body begins to deform!", "The shapeshifter turns into a spider!"),

        /**
         * The Third.
         */
        THIRD(899, "The shapeshifter's body begins to twist!", "The shapeshifter turns into a bear!"),

        /**
         * The Fourth.
         */
        FOURTH(900, "The shapeshifter's body pulses!", "The shapeshifter turns into a wolf!"),

        /**
         * The End.
         */
        END(-1, "You finally kill the shapeshifter once and for all.");

        private val message: Array<String>

        /**
         * Transform.
         *
         * @param npc    the npc
         * @param player the player
         */
        fun transform(npc: WitchExperimentNPC, player: Player?) {
            val newType = next()
            npc.lock()
            npc.pulseManager.clear()
            npc.walkingQueue.reset()
            player!!.setAttribute("/save:witchs_house:experiment_id", id)
            Pulser.submit(object : Pulse(1, npc, player) {
                var counter = 0
                override fun pulse(): Boolean {
                    when (++counter) {
                        1 -> {
                            npc.unlock()
                            npc.animator.reset()
                            npc.fullRestore()
                            npc.type = newType
                            npc.transform(newType.id)
                            npc.impactHandler.disabledTicks = 1
                            if (newType != END) {
                                npc.properties.combatPulse.attack(player)
                            }
                            if (newType.getMessage() != null && newType != END) {
                                player.sendMessage(newType.getMessage()!![0])
                                player.sendMessage(newType.getMessage()!![1])
                            }
                            if (newType == END) {
                                player.setAttribute("witchs_house:experiment_killed", true)
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

        init {
            this.message = message as Array<String>
        }

        /**
         * Next experiment type.
         *
         * @return the experiment type
         */
        operator fun next(): ExperimentType {
            return experimentTypes[ordinal + 1]
        }

        /**
         * Get message string.
         *
         * @return the string.
         */
        fun getMessage(): Array<String>? {
            return message
        }

        companion object {
            /**
             * For id experiment type.
             *
             * @param id the id
             * @return the experiment type
             */
            fun forId(id: Int): ExperimentType? {
                for (type in values()) {
                    if (type.id == id) {
                        return type
                    }
                }
                return null
            }

            private val experimentTypes = values()
        }
    }
}