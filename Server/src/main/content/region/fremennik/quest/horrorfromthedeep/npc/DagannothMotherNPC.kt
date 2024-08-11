package content.region.fremennik.quest.horrorfromthedeep.npc

import content.region.fremennik.quest.horrorfromthedeep.dialogue.JossikDialogueFile
import core.api.*
import core.api.consts.Items
import core.api.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.map.Location
import core.tools.RandomFunction

/**
 * Dagannoth mother NPC.
 */
class DagannothMotherNPC(id: Int = 0, location: Location? = null, session: DagannothSession? = null) :
    AbstractNPC(id, location) {
    private val airSpells = intArrayOf(1, 10, 24, 45)
    private val waterSpells = intArrayOf(4, 14, 27, 48)
    private val earthSpells = intArrayOf(6, 17, 33, 52)
    private val fireSpells = intArrayOf(8, 20, 38, 55)

    val session: DagannothSession?
    var type: DagannothType?
    var isSpawned = false

    init {
        this.isWalks = true
        this.session = session
        this.isRespawn = false
        type = DagannothType.forId(id)
    }

    override fun init() {
        super.init()
        Pulser.submit(DagannothTransform(session!!.player, this))
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (session == null) {
            return
        }
        if (!session.player.isActive) {
            clear()
            return
        }
        if (isSpawned && !properties.combatPulse.isAttacking) {
            properties.combatPulse.attack(session.player)
            this.getSkills().isLifepointsUpdate = false
        }

        if (RandomFunction.random(1, 30) == 5) {
            type!!.transform(this, session.player)
            playAudio(session.player, 1617)
        }
        return
    }

    /**
     * Check what spell player use.
     */
    override fun checkImpact(state: BattleState) {
        if (state.attacker is Player) {
            if (state.victim is NPC) {
                if (type!!.npcId == NPCs.DAGANNOTH_MOTHER_1351) {
                    if (state.style != CombatStyle.MAGIC) {
                        state.neutralizeHits()
                        return
                    }
                    if (state.spell == null) {
                        state.neutralizeHits()
                        return
                    }
                    val spell = state.spell
                    for (id in airSpells) {
                        if (id == spell.spellId) {
                            state.estimatedHit = state.maximumHit
                            return
                        }
                    }
                    state.neutralizeHits()
                }

                if (type!!.npcId == NPCs.DAGANNOTH_MOTHER_1352) {
                    if (state.style != CombatStyle.MAGIC) {
                        state.neutralizeHits()
                        return
                    }
                    if (state.spell == null) {
                        state.neutralizeHits()
                        return
                    }
                    val spell = state.spell
                    for (id in waterSpells) {
                        if (id == spell.spellId) {
                            state.estimatedHit = state.maximumHit
                            return
                        }
                    }
                    state.neutralizeHits()
                }

                if (type!!.npcId == NPCs.DAGANNOTH_MOTHER_1353) {
                    if (state.style != CombatStyle.MAGIC) {
                        state.neutralizeHits()
                        return
                    }
                    if (state.spell == null) {
                        state.neutralizeHits()
                        return
                    }
                    val spell = state.spell
                    for (id in fireSpells) {
                        if (id == spell.spellId) {
                            state.estimatedHit = state.maximumHit
                            return
                        }
                    }
                    state.neutralizeHits()
                }

                if (type!!.npcId == NPCs.DAGANNOTH_MOTHER_1354) {
                    if (state.style != CombatStyle.MAGIC) {
                        state.neutralizeHits()
                        return
                    }
                    if (state.spell == null) {
                        state.neutralizeHits()
                        return
                    }
                    val spell = state.spell
                    for (id in earthSpells) {
                        if (id == spell.spellId) {
                            state.estimatedHit = state.maximumHit
                            return
                        }
                    }
                    state.neutralizeHits()
                }

                if (type!!.npcId == NPCs.DAGANNOTH_MOTHER_1355) {
                    if (state.style != CombatStyle.RANGE) {
                        state.neutralizeHits()
                        return
                    }
                    if (state.style == CombatStyle.RANGE) {
                        state.estimatedHit = state.maximumHit
                        return
                    }
                    state.neutralizeHits()
                }

                if (type!!.npcId == NPCs.DAGANNOTH_MOTHER_1356) {
                    if (state.style != CombatStyle.MELEE) {
                        state.neutralizeHits()
                        return
                    }
                    if (state.style == CombatStyle.MELEE) {
                        state.estimatedHit = state.maximumHit
                        return
                    }
                    state.neutralizeHits()
                }
            }
        }
    }

    override fun finalizeDeath(killer: Entity?) {
        super.finalizeDeath(killer)
        if (killer is Player) {
            clearHintIcon(killer)
            val hasCasket = hasAnItem(killer, Items.RUSTY_CASKET_3849).container != null
            teleport(killer, Location.create(2515, 4625, 1))
            finishQuest(killer, "Horror from the Deep")
            lock(killer, 10)
            lockInteractions(killer, 10)
            if (!hasCasket) {
                addItemOrDrop(killer, Items.RUSTY_CASKET_3849)
            }
            runTask(killer, 5) {
                closeInterface(killer)
                openDialogue(killer, JossikDialogueFile())
            }
        }
        clear()
        super.finalizeDeath(killer)
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return DagannothMotherNPC(id, location, null)
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        if (session == null) {
            return false
        }
        return session.player == entity
    }

    override fun canSelectTarget(target: Entity): Boolean {
        if (target is Player) {
            if (target != session!!.player) {
                return false
            }
        }
        return true
    }

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.DAGANNOTH_MOTHER_1351,
            NPCs.DAGANNOTH_MOTHER_1352,
            NPCs.DAGANNOTH_MOTHER_1353,
            NPCs.DAGANNOTH_MOTHER_1354,
            NPCs.DAGANNOTH_MOTHER_1355,
            NPCs.DAGANNOTH_MOTHER_1356,
        )
    }

    /**
     * Enum class representing different types of Dagannoth.
     *
     * @property npcId The ID of the NPC.
     * @property sendChat The chat message to send.
     * @property sendMessage The message to send.
     * @constructor Initializes a Dagannoth type with the specified properties.
     */
    enum class DagannothType(var npcId: Int, var sendChat: String?, var sendMessage: String?) {
        /**
         * White.
         */
        WHITE(NPCs.DAGANNOTH_MOTHER_1351, "Tktktktktktkt", null),

        /**
         * Blue.
         */
        BLUE(NPCs.DAGANNOTH_MOTHER_1352, "Krrrrrrk", "the dagannoth changes to blue..."),

        /**
         * Red.
         */
        RED(NPCs.DAGANNOTH_MOTHER_1353, "Sssssrrrkkkkk", "the dagannoth changes to red..."),

        /**
         * Brown.
         */
        BROWN(NPCs.DAGANNOTH_MOTHER_1354, "Krrrrrrssssssss", "the dagannoth changes to brown..."),

        /**
         * Green.
         */
        GREEN(NPCs.DAGANNOTH_MOTHER_1355, "Krkrkrkrkrkrkrkr", "the dagannoth changes to green..."),

        /**
         * Orange.
         */
        ORANGE(NPCs.DAGANNOTH_MOTHER_1356, "Chkhkhkhkhk", "the dagannoth changes to orange...");

        /**
         * This function transforms a Dagannoth Mother NPC into a different form based on certain conditions.
         *
         * @param dagannoth The Dagannoth Mother NPC to be transformed.
         * @param player The player triggering the transformation.
         */
        fun transform(dagannoth: DagannothMotherNPC, player: Player) {
            val newType = next()
            val oldHp = dagannoth.getSkills().lifepoints
            dagannoth.type = newType
            dagannoth.transform(newType.npcId)
            dagannoth.skills.isLifepointsUpdate = false
            Pulser.submit(DagannothTransform(player, dagannoth))
            dagannoth.getSkills().setLifepoints(oldHp)
        }

        /**
         * Next.
         *
         * @return A random DagannothType.
         */
        operator fun next(): DagannothType {
            return values().random()
        }

        companion object {
            /**
             * Get DagannothType for the given id.
             *
             * @param id The id of the DagannothType.
             * @return The DagannothType corresponding to the id, or null if not found.
             */
            fun forId(id: Int): DagannothType? {
                for (type in values()) {
                    if (type.npcId == id) {
                        return type
                    }
                }
                return null
            }
        }
    }

    /**
     * Dagannoth transform.
     *
     * @property player The player object.
     * @property dagannoth The DagannothMotherNPC object.
     * @constructor Creates a DagannothTransform object.
     */
    class DagannothTransform(val player: Player?, val dagannoth: DagannothMotherNPC) : Pulse() {
        var counter = 0
        override fun pulse(): Boolean {
            when (counter++) {
                0 -> {
                    registerHintIcon(player!!, dagannoth)
                    player.sendMessage(dagannoth.type?.sendMessage)
                    dagannoth.attack(player).also { dagannoth.sendChat(dagannoth.type?.sendChat) }
                }
            }
            return false
        }
    }
}
