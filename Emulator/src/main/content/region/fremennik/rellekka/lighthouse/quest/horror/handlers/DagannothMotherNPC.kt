package content.region.fremennik.rellekka.lighthouse.quest.horror.handlers

import content.region.fremennik.rellekka.lighthouse.quest.horror.dialogue.JossikDialogueFile
import core.api.*
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
import org.rs.consts.Items
import org.rs.consts.NPCs
import org.rs.consts.QuestName

/**
 * Handles the Dagannoth mother NPC.
 */
class DagannothMotherNPC(id: Int = 0, location: Location? = null, session: DagannothSession? = null) :
    AbstractNPC(id, location) {
    private val airSpells = intArrayOf(1, 10, 24, 45)
    private val waterSpells = intArrayOf(4, 14, 27, 48)
    private val earthSpells = intArrayOf(6, 17, 33, 52)
    private val fireSpells = intArrayOf(8, 20, 38, 55)

    /**
     * The session.
     */
    val session: DagannothSession?

    /**
     * The Dagannoth type.
     */
    var type: DagannothType?

    /**
     * If the fight has started.
     */
    var isSpawned = false

    /**
     * Constructs a new `DagannothMotherNPC` `Object`.
     */
    init {
        this.isWalks = true
        this.session = session
        this.isRespawn = false
        type = DagannothType.forId(id)
    }

    override fun init() {
        super.init()
        if (session?.player?.location?.regionId == 10056)
            Pulser.submit(DagannothTransform(session.player, this))
        else session?.close()
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
     * Check what spell player use for each Dagannoth NPC type.
     */
    override fun checkImpact(state: BattleState) {
        if (state.attacker is Player) {
            if (state.victim is NPC) {

                /**
                 * White type.
                 */
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

                /**
                 * Blue type.
                 */
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

                /**
                 * Red type.
                 */
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

                /**
                 * Brown type.
                 */
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

                /**
                 * Green type.
                 */
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

                /**
                 * Gold type.
                 */
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
            finishQuest(killer, QuestName.HORROR_FROM_THE_DEEP)
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
     * Represents types of Dagannoth NPC.
     *
     * @param npcId The ID of the NPC.
     * @param sendChat The chat message to send.
     * @param sendMessage The message to send.
     * @constructor dagannoth type.
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
         * Transforms the new npc.
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
         * Gets the next random type.
         * @return returns a random type.
         */
        operator fun next(): DagannothType {
            return values().random()
        }

        companion object {
            /**
             * Gets the Dagannoth type for the id.
             *
             * @param id the id.
             * @return the Dagannoth type.
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
     * Transforms the new npc.
     *
     * @param player The player.
     * @param dagannoth The npc.
     * @return new NPC.
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
