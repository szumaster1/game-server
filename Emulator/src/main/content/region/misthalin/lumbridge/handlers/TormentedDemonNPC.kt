package content.region.misthalin.lumbridge.handlers

import content.data.BossKillCounter.Companion.addtoKillcount
import org.rs.consts.NPCs
import core.game.node.entity.Entity
import core.game.node.entity.combat.BattleState
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.CombatSwingHandler
import core.game.node.entity.combat.InteractionType
import core.game.node.entity.impl.Animator.Priority
import core.game.node.entity.impl.Projectile
import core.game.node.entity.npc.AbstractNPC
import core.game.node.entity.player.Player
import core.game.world.map.Location
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.tools.RandomFunction
import java.util.concurrent.TimeUnit

/**
 * Represents the Tormented demon NPC.
 */
@Initializable
class TormentedDemonNPC @JvmOverloads constructor(id: Int = -1, location: Location? = null) : AbstractNPC(id, location) {

    private val TD_SWING_HANDLER = TormentedDemonSwingHandler()
    private var lastSwitch = System.currentTimeMillis() + 15000
    private var fireShield = true
    private var shieldDelay: Long = 0
    private val damageLog = IntArray(3)

    override fun init() {
        super.init()
        getAggressiveHandler().chanceRatio = 10
        getAggressiveHandler().radius = 64
        getAggressiveHandler().isAllowTolerance = false
    }

    init {
        isWalks = true
        isAggressive = true
        this.setDefaultBehavior()
    }

    override fun shouldPreventStacking(other: Entity): Boolean {
        return other is TormentedDemonNPC
    }

    override fun handleTickActions() {
        super.handleTickActions()
        if (!fireShield && shieldDelay < System.currentTimeMillis() && shieldDelay > 0) {
            val p = getAttribute<Player>("shield-player", null)
            fireShield = true
            shieldDelay = 0
            if (p != null && p.isActive && p.location.withinDistance(getLocation()) && isActive && !isHidden(p)) {
                p.sendMessage("The Tormented demon regains its strength against your weapon.")
            }
        }
    }

    override fun sendImpact(state: BattleState) {
        var max = 0
        max = when (state.style) {
            CombatStyle.MAGIC, CombatStyle.RANGE -> 26
            CombatStyle.MELEE -> 18
        }
        if (state.estimatedHit > max) {
            state.estimatedHit = RandomFunction.random(max - 5)
        }
    }

    override fun checkImpact(state: BattleState) {
        /**
         * Use the formatted hit to ensure protection prayers
         * are applied (i.e. can't darklight while the demon is praying melee).
         */
        val formattedHit = state.attacker.getFormattedHit(state, state.estimatedHit).toInt()
        if (state.attacker.isPlayer && formattedHit > 0 && state.weapon != null && (state.weapon.id == 6746 || state.weapon.id == 732)) {
            /**
             * The message doesn't get sent twice, but additional
             * darklight strikes while the shield is down do delay
             * the shield's return.
             */
            if (fireShield) {
                state.attacker.asPlayer().sendMessage("The demon is temporarily weakened by your weapon.")
            }
            shieldDelay = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60)
            fireShield = false
            setAttribute("shield-player", state.attacker)
        }
        if (fireShield) {
            state.estimatedHit = (state.estimatedHit * 0.25).toInt()
            graphics(Graphic.create(1885))
        }
        if (state.style == null) {
            return
        }

        /**
         * Use formattedHit for the prayer swap calculation since it's before the fire
         * shield reduction was applied (a ranged hit of 8 through the shield corresponds to a
         * pre-shield hit of 32, which should cause the demon to switch to praying range).
         */
        val hit = if (formattedHit > 0) formattedHit else 1
        damageLog[state.style.ordinal] = damageLog[state.style.ordinal] + hit
    }

    override fun onImpact(entity: Entity, state: BattleState) {
        /**
         * Call the parent class's onImpact handler to ensure that
         * retaliation happens if the TD is non-aggressive.
         */
        super.onImpact(entity, state)

        /**
         * The demon will switch prayers after it receives 31 damage from one attack style.
         * This is done in onImpact so that it happens after the damage that caused the switch is dealt.
         */
        val damaged = mostDamagedStyle
        if (damaged != null && damageLog[damaged.ordinal] >= 31 && damaged != properties.protectStyle) {
            for (i in 0..2) {
                damageLog[i] = 0
            }
            transformDemon(null, damaged)
            return
        } else if (lastSwitch < System.currentTimeMillis()) {
            transformDemon(RandomFunction.getRandomElement(getAlternateStyle(TD_SWING_HANDLER.style)), null)
            lastSwitch = System.currentTimeMillis() + 15000

            /**
             * The roar animation that TDs do when they change attack styles
             * shouldn't be interrupted by attack/defence animations.
             * Source: https://youtu.be/VcWncVTev1s?t=220
             */
            animate(Animation(10917, Priority.HIGH))
        }
    }

    override fun finalizeDeath(killer: Entity) {
        super.finalizeDeath(killer)
        reTransform()
        fireShield = true
        addtoKillcount(killer as Player, this.id)
    }

    override fun construct(id: Int, location: Location, vararg objects: Any): AbstractNPC {
        return TormentedDemonNPC(id, location)
    }

    override fun getSwingHandler(hit: Boolean): CombatSwingHandler {
        return TD_SWING_HANDLER
    }

    /**
     * Transform demon.
     *
     * @param attackStyle the attack style (combat style).
     * @param protectionStyle the protection style (combat style).
     */
    fun transformDemon(attackStyle: CombatStyle?, protectionStyle: CombatStyle?) {
        /**
         * If either attackStyle or protectionStyle are
         * null, use the current form's values.
         */
        var attackStyle = attackStyle
        var protectionStyle = protectionStyle
        if (attackStyle == null) {
            attackStyle = properties.combatPulse.style
        }
        if (protectionStyle == null) {
            protectionStyle = properties.protectStyle
        }
        val id = getCombatStyleDemon(protectionStyle, attackStyle)
        val oldHp = getSkills().lifepoints
        transform(id)
        getSkills().lifepoints = oldHp

        TD_SWING_HANDLER.style = properties.combatPulse.style
    }

    val mostDamagedStyle: CombatStyle?
        /**
         * Gets most damaged style.
         */
        get() {
            var highestDamage = 0
            var style: CombatStyle? = null
            for (i in damageLog.indices) {
                if (damageLog[i] > highestDamage) {
                    highestDamage = damageLog[i]
                    style = CombatStyle.values()[i]
                }
            }
            return style
        }

    /**
     * Gets combat style demon.
     *
     * @param protection the protection.
     * @param style the combat style.
     * @return
     */
    fun getCombatStyleDemon(protection: CombatStyle?, style: CombatStyle): Int {
        return getDemonIds(protection)[2 - style.ordinal]
    }

    /**
     * Get demon ids.
     *
     * @param style the style.
     * @return
     */
    fun getDemonIds(style: CombatStyle?): IntArray {
        val ids = if (style == CombatStyle.MELEE) MELEE else if (style == CombatStyle.RANGE) RANGE else MAGE
        return ids[if (startId == getIds()[0]) 0 else 1]
    }

    /**
     * Get alternate style.
     *
     * @param style the style.
     * @return
     */
    fun getAlternateStyle(style: CombatStyle): Array<CombatStyle?> {
        val styles = arrayOfNulls<CombatStyle>(2)
        var index = 0
        for (i in CombatStyle.values().indices) {
            if (CombatStyle.values()[i] != style) {
                styles[index] = CombatStyle.values()[i]
                index++
            }
        }
        return styles
    }

    val startId: Int
        get() = if (id <= 8357) ids[0] else ids[10]

    override fun getIds(): IntArray {
        return intArrayOf(
            NPCs.TORMENTED_DEMON_8349,
            NPCs.TORMENTED_DEMON_8350,
            NPCs.TORMENTED_DEMON_8351,
            NPCs.TORMENTED_DEMON_8352,
            NPCs.TORMENTED_DEMON_8353,
            NPCs.TORMENTED_DEMON_8354,
            NPCs.TORMENTED_DEMON_8355,
            NPCs.TORMENTED_DEMON_8356,
            NPCs.TORMENTED_DEMON_8357,
            NPCs.TORMENTED_DEMON_8358,
            NPCs.TORMENTED_DEMON_8359,
            NPCs.TORMENTED_DEMON_8360,
            NPCs.TORMENTED_DEMON_8361,
            NPCs.TORMENTED_DEMON_8362,
            NPCs.TORMENTED_DEMON_8363,
            NPCs.TORMENTED_DEMON_8364,
            NPCs.TORMENTED_DEMON_8365,
            NPCs.TORMENTED_DEMON_8366
        )
    }

    /**
     * The Tormented demon swing handler.
     */
    inner class TormentedDemonSwingHandler : CombatSwingHandler(CombatStyle.MELEE) {
        var style: CombatStyle = CombatStyle.MELEE

        override fun canSwing(entity: Entity, victim: Entity): InteractionType? {
            return style.swingHandler.canSwing(entity, victim)
        }

        override fun swing(entity: Entity?, victim: Entity?, state: BattleState?): Int {
            return style.swingHandler.swing(entity, victim, state)
        }

        override fun impact(entity: Entity?, victim: Entity?, state: BattleState?) {
            style.swingHandler.impact(entity, victim, state)
        }

        override fun visualizeImpact(entity: Entity?, victim: Entity?, state: BattleState?) {
            style.swingHandler.visualizeImpact(entity, victim, state)
        }

        override fun visualize(entity: Entity, victim: Entity?, state: BattleState?) {
            when (style) {
                CombatStyle.MELEE -> {
                    entity.animate(entity.properties.attackAnimation)
                    entity.graphics(Graphic.create(1886))
                }

                CombatStyle.RANGE -> {
                    Projectile.ranged(entity, victim, 1887, 88, 36, 50, 15).send()
                    entity.animate(entity.properties.rangeAnimation)
                }

                CombatStyle.MAGIC -> {
                    Projectile.magic(entity, victim, 1884, 88, 36, 50, 15).send()
                    entity.animate(entity.properties.magicAnimation)
                }
            }
        }

        override fun calculateAccuracy(entity: Entity?): Int {
            return style.swingHandler.calculateAccuracy(entity)
        }

        override fun calculateHit(entity: Entity?, victim: Entity?, modifier: Double): Int {
            return style.swingHandler.calculateHit(entity, victim, modifier)
        }

        override fun calculateDefence(victim: Entity?, attacker: Entity?): Int {
            return style.swingHandler.calculateDefence(victim, attacker)
        }

        override fun getSetMultiplier(e: Entity?, skillId: Int): Double {
            return style.swingHandler.getSetMultiplier(e, skillId)
        }
    }

    companion object {
        private val MELEE = arrayOf(intArrayOf(8349, 8352, 8355), intArrayOf(8358, 8361, 8364))
        private val MAGE = arrayOf(intArrayOf(8350, 8353, 8356), intArrayOf(8359, 8362, 8365))
        private val RANGE = arrayOf(intArrayOf(8351, 8354, 8357), intArrayOf(8360, 8363, 8366))
    }
}
