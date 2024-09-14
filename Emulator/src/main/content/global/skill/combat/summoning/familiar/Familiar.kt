package content.global.skill.combat.summoning.familiar

import cfg.consts.Sounds
import content.global.skill.combat.summoning.SummoningPouch
import content.global.skill.combat.summoning.SummoningPouch.Companion.get
import content.global.skill.combat.summoning.SummoningScroll.Companion.forPouch
import content.global.skill.combat.summoning.pet.Pet
import core.api.log
import core.api.playAudio
import core.api.setVarbit
import core.api.setVarp
import core.cache.def.impl.NPCDefinition
import core.game.interaction.MovementPulse
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.impl.PulseType
import core.game.node.entity.npc.NPC
import core.game.node.entity.player.Player
import core.game.node.entity.skill.SkillBonus
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld
import core.game.world.GameWorld.Pulser
import core.game.world.GameWorld.ticks
import core.game.world.map.Location
import core.game.world.map.RegionManager.getSpawnLocation
import core.game.world.map.path.Pathfinder
import core.game.world.map.zone.ZoneRestriction
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Plugin
import core.tools.Log
import core.tools.RandomFunction
import kotlin.math.floor

/**
 * Represents a familiar.
 *
 * @author Emperor
 */
abstract class Familiar @JvmOverloads constructor(
    /**
     * The owner.
     */
    @JvmField var owner: Player, id: Int,
    /**
     * The initial amount of ticks.
     */
    protected var maximumTicks: Int, pouchId: Int, specialCost: Int, attackStyle: Int = WeaponInterface.STYLE_DEFENSIVE
) :
    NPC(id, null), Plugin<Any?> {
    /**
     * Gets the owner.
     *
     * @return The owner.
     */
    /**
     * Sets the owner.
     *
     * @param owner The owner to set.
     */

    /**
     * Gets ticks.
     *
     * @return the ticks
     */
    /**
     * The amount of ticks left.
     */
    @JvmField
    var ticks: Int = 0

    /**
     * Gets special points.
     *
     * @return the special points
     */
    /**
     * The amount of special points left.
     */
    @JvmField
    var specialPoints: Int = 60

    /**
     * Gets the pouch id.
     *
     * @return The pouch id.
     */
    /**
     * The pouch id.
     */
    @JvmField
    val pouchId: Int

    /**
     * The special move cost.
     */
    private val specialCost: Int

    private val pouch: SummoningPouch?

    /**
     * Gets the combatHandler.
     *
     * @return The combatHandler.
     */
    /**
     * Sets the combatHandler.
     *
     * @param combatHandler The combatHandler to set.
     */
    /**
     * The combat reward.
     */
    var combatHandler: CombatSwingHandler? = null

    /**
     * Checks if the familiar is a combat familiar.
     *
     * @return `True` if so.
     */
    /**
     * If the familiar is a combat familiar.
     */
    open var isCombatFamiliar: Boolean = false
        protected set

    /**
     * If the familiars special is charged.
     */
    protected var charged: Boolean = false

    /**
     * The invisible familiar boosts.
     */
    protected var boosts: List<SkillBonus> = ArrayList(20)

    /**
     * Gets the exp style.
     *
     * @return the style.
     */
    /**
     * The attack style.
     */
    val attackStyle: Int

    private var firstCall = true

    /**
     * Constructs a new `Familiar` `Object`.
     *
     * @param owner       The owner.
     * @param id          The NPC id.
     * @param maximumTicks       The ticks left.
     * @param pouchId     The pouch.
     * @param specialCost The special move cost.
     * @param attackStyle the style.
     */
    /**
     * Constructs a new `Familiar` `Object`.
     *
     * @param owner       The owner.
     * @param id          The NPC id.
     * @param ticks       The ticks left.
     * @param pouchId     The pouch.
     * @param specialCost The special move cost.
     */
    init {
        this.maximumTicks = maximumTicks
        this.pouchId = pouchId
        this.pouch = get(pouchId)
        this.specialCost = specialCost
        this.isCombatFamiliar = NPCDefinition.forId(originalId + 1).name == getName()
        this.attackStyle = attackStyle
    }

    /**
     * Creates the familiar.
     *
     * @param loc  The location.
     * @param call the call
     */
    fun init(loc: Location, call: Boolean) {
        location = loc
        if (location == null) {
            location = owner.location
            isInvisible = true
        }
        super.init()
        startFollowing()
        sendConfiguration()
        if (call) {
            call()
        }
        owner.interfaceManager.openInfoBars()
        if (zoneMonitor.isInZone("Wilderness")) {
            transform()
        }
    }

    override fun init() {
        init(spawnLocation!!, true)
    }

    override fun handleTickActions() {
        if (maximumTicks-- % 50 == 0) {
            updateSpecialPoints(-15)
            owner.getSkills().updateLevel(Skills.SUMMONING, -1, 0)
            if (!text.isEmpty()) {
                super.sendChat(text)
            }
        }
        sendTimeRemaining()
        when (maximumTicks) {
            100 -> owner.packetDispatch.sendMessage("<col=ff0000>You have 1 minute before your familiar vanishes.")
            50 -> owner.packetDispatch.sendMessage("<col=ff0000>You have 30 seconds before your familiar vanishes.")
            0 -> {
                if (isBurdenBeast && !(this as BurdenBeast).container.isEmpty) {
                    owner.packetDispatch.sendMessage("<col=ff0000>Your familiar has dropped all the items it was holding.")
                } else {
                    owner.packetDispatch.sendMessage("<col=ff0000>Your familiar has vanished.")
                }
                dismiss()
                return
            }
        }
        val combat = owner.properties.combatPulse
        if (!isInvisible && !properties.combatPulse.isAttacking && (combat.isAttacking || owner.inCombat())) {
            var victim = combat.getVictim()
            if (victim == null) {
                victim = owner.getAttribute("combat-attacker")
            }
            if (combat.getVictim() !== this && victim != null && !victim.isInvisible && properties.isMultiZone && owner.properties.isMultiZone && isCombatFamiliar && !isBurdenBeast && !isPeacefulFamiliar) {
                properties.combatPulse.attack(victim)
            }
        }
        if ((!isInvisible && owner.location.getDistance(getLocation()) > 12) || (isInvisible && maximumTicks % 25 == 0)) {
            if (!call()) {
                isInvisible = true
            }
        } else if (!pulseManager.hasPulseRunning()) {
            startFollowing()
        }
        handleFamiliarTick()
    }

    override fun isAttackable(entity: Entity, style: CombatStyle, message: Boolean): Boolean {
        if (entity === owner) {
            if (message) {
                owner.packetDispatch.sendMessage("You can't just betray your own familiar like that!")
            }
            return false
        }
        if (entity is Player) {
            if (!owner.isAttackable(entity, style, message)) {
                return false
            }
        }
        if (!properties.isMultiZone) {
            if (entity is Player && !entity.properties.isMultiZone) {
                if (message) {
                    entity.packetDispatch.sendMessage("You have to be in multicombat to attack a player's familiar.")
                }
                return false
            }
            if (entity is Player) {
                if (message) {
                    entity.packetDispatch.sendMessage("This familiar is not in the a multicombat zone.")
                }
            }
            return false
        }
        if (entity is Player) {
            if (!entity.skullManager.isWilderness) {
                if (message) {
                    entity.packetDispatch.sendMessage("You have to be in the wilderness to attack a player's familiar.")
                }
                return false
            }
            if (!owner.skullManager.isWilderness) {
                if (message) {
                    entity.packetDispatch.sendMessage("This familiar's owner is not in the wilderness.")
                }
                return false
            }
        }
        return super.isAttackable(entity, style, message)
    }

    override fun onRegionInactivity() {
        call()
    }

    override fun getSwingHandler(swing: Boolean): CombatSwingHandler {
        if (combatHandler != null) {
            return combatHandler as CombatSwingHandler
        }
        return super.getSwingHandler(swing)
    }

    /**
     * Constructs a new `Familiar` `Object`.
     *
     * @param owner The owner.
     * @param id    The NPC id.
     * @return The familiar.
     */
    abstract fun construct(owner: Player?, id: Int): Familiar?

    /**
     * Executes the special move.
     *
     * @param special The familiar special object.
     * @return `True` if the move was executed.
     */
    protected abstract fun specialMove(special: FamiliarSpecial?): Boolean

    /**
     * Handles the familiar special tick.
     */
    protected open fun handleFamiliarTick() {
    }

    /**
     * Configures use with events, and other plugin related content..
     */
    protected open fun configureFamiliar() {
    }

    protected open val text: String
        /**
         * Gets the forced chat text for this familiar.
         *
         * @return The forced chat text.
         */
        get() = ""

    /**
     * Transforms the familiar into the Wilderness combat form.
     */
    fun transform() {
        if (isCombatFamiliar) {
            transform(originalId + 1)
        }
    }

    /**
     * Refresh timer.
     */
    fun refreshTimer() {
        maximumTicks = maximumTicks
    }

    /**
     * Sends the time remaining.
     */
    private fun sendTimeRemaining() {
        val minutes = maximumTicks / 100
        val centiminutes = maximumTicks % 100
        setVarbit(owner, 4534, minutes)
        setVarbit(owner, 4290, if (centiminutes > 49) 1 else 0)
    }

    /**
     * Checks if the familiar can execute its special move and does so if able.
     *
     * @param special The familiar special object.
     * @return the boolean
     */
    fun executeSpecialMove(special: FamiliarSpecial): Boolean {
        if (special.node === this) {
            return false
        }
        if (specialCost > specialPoints) {
            owner.packetDispatch.sendMessage("Your familiar does not have enough special move points left.")
            return false
        }
        val scroll = forPouch(pouchId)
        if (scroll == null) {
            owner.packetDispatch.sendMessage("Invalid scroll for pouch $pouchId - report!")
            return false
        }
        if (!owner.inventory.contains(scroll.itemId, 1)) {
            owner.packetDispatch.sendMessage("You do not have enough scrolls left to do this special move.")
            return false
        }
        if (owner.location.getDistance(getLocation()) > 15) {
            owner.packetDispatch.sendMessage("Your familiar is too far away to use that scroll, or it cannot see you.")
            return false
        }
        if (specialMove(special)) {
            setAttribute("special-delay", GameWorld.ticks + 3)
            owner.inventory.remove(Item(scroll.itemId))
            playAudio(owner, Sounds.SPELL_4161)
            visualizeSpecialMove()
            updateSpecialPoints(specialCost)
            owner.getSkills().addExperience(Skills.SUMMONING, scroll.experience, true)
        }
        return true
    }

    /**
     * Sends the special move visualization for the owner.
     */
    open fun visualizeSpecialMove() {
        owner.visualize(Animation.create(7660), Graphic.create(1316))
    }

    /**
     * Sends a familiar hit.
     *
     * @param target   the target.
     * @param maxHit   the max hit.
     * @param graphics the graphics.
     */
    /**
     * Sends a familiar hit.
     *
     * @param target the target
     * @param maxHit the max hit.
     */
    @JvmOverloads
    fun sendFamiliarHit(target: Entity, maxHit: Int, graphics: Graphic? = null) {
        val ticks = 2 + floor(getLocation().getDistance(target.location) * 0.5) as Int
        properties.combatPulse.setNextAttack(4)
        Pulser.submit(object : Pulse(ticks, this, target) {
            override fun pulse(): Boolean {
                val state = BattleState(this@Familiar, target)
                var hit = 0
                if (combatStyle.swingHandler.isAccurateImpact(this@Familiar, target)) {
                    hit = RandomFunction.randomize(maxHit)
                }
                state.estimatedHit = hit
                target.impactHandler.handleImpact(owner, hit, CombatStyle.MELEE, state)
                if (graphics != null) {
                    target.graphics(graphics)
                }
                return true
            }
        })
    }

    /**
     * Sends a projectile to the target.
     *
     * @param target       the target.
     * @param projectileId the projectile id.
     */
    fun projectile(target: Entity, projectileId: Int) {
        Projectile.magic(this, target, projectileId, 40, 36, 51, 10).send()
    }

    /**
     * Checks if this familiar can attack the target (used mainly for special
     * moves).
     *
     * @param target  the target
     * @param message the message
     * @return the boolean
     */
    fun canAttack(target: Entity, message: Boolean): Boolean {
        if (!target.isAttackable(owner, owner.properties.combatPulse.style, true)) {
            return false
        }
        if (target.location.getDistance(getLocation()) > 8) {
            if (message) {
                owner.packetDispatch.sendMessage("That target is too far.")
            }
            return false
        }
        if (target.locks.isInteractionLocked || !target.isAttackable(this, CombatStyle.MAGIC, true)) {
            return false
        }
        return isCombatFamiliar
    }

    override fun canAttack(target: Entity): Boolean {
        return canAttack(target, true)
    }

    /**
     * Checks if a familiar can perform a combat special attack.
     *
     * @param target  the target.
     * @param message show message.
     * @return `True` if so.
     */
    /**
     * Checks if a faimiliar can perform a combat special attack.
     *
     * @param target the target.
     * @return `True` if so.
     */
    @JvmOverloads
    fun canCombatSpecial(target: Entity, message: Boolean = true): Boolean {
        if (!canAttack(target, message)) {
            return false
        }
        if (!isOwnerAttackable) {
            return false
        }
        if (getAttribute("special-delay", 0) > GameWorld.ticks) {
            return false
        }
        return true
    }

    val isOwnerAttackable: Boolean
        /**
         * Checks if the owner is attackable.
         *
         * @return `True` if so.
         */
        get() {
            if (!owner.properties
                    .combatPulse.isAttacking && !owner.inCombat() && !properties.combatPulse.isAttacking
            ) {
                owner.packetDispatch.sendMessage("Your familiar cannot fight whilst you are not in combat.")
                return false
            }
            return true
        }

    open val combatStyle: CombatStyle
        /**
         * Gets the combat style.
         *
         * @return the style.
         */
        get() = CombatStyle.MAGIC

    /**
     * Adjusts a players battle state.
     *
     * @param state the state.
     */
    open fun adjustPlayerBattle(state: BattleState?) {
    }

    /**
     * Starts following the owner.
     */
    open fun startFollowing() {
        pulseManager.run(object : MovementPulse(this, owner, Pathfinder.DUMB) {
            override fun pulse(): Boolean {
                return false
            }
        }, PulseType.STANDARD)
        face(owner)
    }

    override fun finalizeDeath(killer: Entity) {
        dismiss()
    }

    /**
     * Sends the familiar packets.
     */
    open fun sendConfiguration() {
        setVarp(owner, 448, pouchId)
        setVarp(owner, 1174, originalId)
        setVarp(owner, 1175, specialCost shl 23)
        sendTimeRemaining()
        updateSpecialPoints(0)
    }

    /**
     * Calls the familiar.
     *
     * @return the boolean
     */
    //int spamTimer = 0;
    open fun call(): Boolean {
        val destination = spawnLocation
            ?: //owner.getPacketDispatch().sendMessage("Your familiar is too big to fit here. Try calling it again when you are standing");
            //owner.getPacketDispatch().sendMessage("somewhere with more space.");
            //spamTimer = 50;
            return false
        isInvisible =
            owner.zoneMonitor.isRestricted(ZoneRestriction.FOLLOWERS) && !owner.locks.isLocked("enable_summoning")
        if (isInvisible) return true
        properties.teleportLocation = destination
        if (this !is Pet) {
            if (firstCall) {
                // TODO: Each familiar has its own initial summon sound that needs to be implemented at some point
                playAudio(owner, Sounds.SUMMON_NPC_188)
                firstCall = false
            } else {
                playAudio(owner, Sounds.SUMMON_NPC_188)
            }
            if (size() > 1) {
                graphics(LARGE_SUMMON_GRAPHIC)
            } else {
                graphics(SMALL_SUMMON_GRAPHIC)
            }
        }
        if (properties.combatPulse.isAttacking) {
            startFollowing()
        } else {
            face(owner)
        }
        if (!isRenderable && owner.isActive) {
            // log(this.getClass(), Log.ERR,  "Familiar in inactive region!");
            walkingQueue.update()
            updateMasks.prepare(this)
        }
        return true
    }

    open val spawnLocation: Location?
        /**
         * Gets the spawning location of the familiar.
         *
         * @return The spawn location.
         */
        get() = getSpawnLocation(owner, this)

    /**
     * Dismisses the familiar.
     */
    open fun dismiss() {
        clear()
        pulseManager.clear()
        owner.interfaceManager.removeTabs(7)
        owner.familiarManager.familiar = null
        setVarp(owner, 448, -1)
        setVarp(owner, 1176, 0)
        setVarp(owner, 1175, 182986)
        setVarp(owner, 1174, -1)
        owner.appearance.sync()
        owner.interfaceManager.setViewedTab(3)
    }

    /**
     * Updates the special move points.
     *
     * @param diff The difference to decrease with.
     */
    fun updateSpecialPoints(diff: Int) {
        specialPoints -= diff
        if (specialPoints > 60) {
            specialPoints = 60
        }
        setVarp(owner, 1177, specialPoints)
    }

    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any?>? {
        for (id in ids) {
            if (FamiliarManager.getFamiliars().containsKey(id)) {
                log(this.javaClass, Log.ERR, "Familiar $id was already registered!")
                return null
            }
            FamiliarManager.getFamiliars()[id] = this
            configureFamiliar()
        }
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    /**
     * Gets the charged.
     *
     * @return The charged.
     */
    open fun isCharged(): Boolean {
        if (charged) {
            owner.packetDispatch.sendMessage("Your familiar is already charging its attack!")
            return true
        }
        return false
    }

    /**
     * Gets a familiar boost.
     *
     * @param skill the skill.
     * @return the boost.
     */
    fun getBoost(skill: Int): Int {
        var bonus: SkillBonus? = null
        for (b in boosts) {
            if (b.skillId == skill) {
                bonus = b
                break
            }
        }
        if (bonus == null) {
            return 0
        }
        return bonus.bonus.toInt()
    }

    /**
     * Charges a familiar.
     */
    fun charge() {
        setCharged(true)
    }

    /**
     * Sets the charged.
     *
     * @param charged The charged to set.
     */
    fun setCharged(charged: Boolean) {
        this.charged = charged
    }

    open val isBurdenBeast: Boolean
        /**
         * Checks if the familiar is a beast of burden.
         *
         * @return `True` if so.
         */
        get() = false

    val isPeacefulFamiliar: Boolean
        /**
         * Is peaceful familiar boolean.
         *
         * @return the boolean
         */
        get() = pouch!!.peaceful

    /**
     * Gets the NPC ids.
     *
     * @return The npc ids.
     */
    abstract val ids: IntArray

    val viewAnimation: Animation?
        /**
         * Gets the view animation for remote viewing.
         *
         * @return the animation.
         */
        get() = null

    companion object {
        /**
         * The summon graphics for a small familiar.
         */
        protected val SMALL_SUMMON_GRAPHIC: Graphic = Graphic.create(1314)

        /**
         * The spawn graphics for a large familiar.
         */
        protected val LARGE_SUMMON_GRAPHIC: Graphic = Graphic.create(1315)

        /**
         * The special animation.
         */
        protected val SPECIAL_ANIMATION: Animation = Animation.create(7660)

        /**
         * The special graphic.
         */
        protected val SPECIAL_GRAPHIC: Graphic = Graphic.create(1316)
    }
}
