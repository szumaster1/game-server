package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import core.api.stun
import core.game.node.entity.Entity
import core.game.node.entity.combat.CombatStyle
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.Initializable
import core.plugin.Plugin
import core.plugin.PluginManager.definePlugin
import core.tools.RandomFunction
import kotlin.math.floor

/**
 * Minotaur familiar npc.
 */
@Initializable
class MinotaurFamiliarNPC : Plugin<Any?> {
    @Throws(Throwable::class)
    override fun newInstance(arg: Any?): Plugin<Any?> {
        definePlugin(BronzeMinotaurNPC())
        definePlugin(IronMinotaurNPC())
        definePlugin(SteelMinotaurNPC())
        definePlugin(MithrilMinotaurNPC())
        definePlugin(AdamantMinotaurNPC())
        definePlugin(RuneMinotaurNPC())
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    /**
     * Bull rush boolean.
     *
     * @param familiar the familiar
     * @param special  the special
     * @param maxHit   the max hit
     * @return the boolean
     */
    fun bullRush(familiar: Familiar, special: FamiliarSpecial, maxHit: Int): Boolean {
        val target = special.node as Entity
        if (!familiar.canCombatSpecial(target)) {
            return false
        }
        familiar.sendFamiliarHit(target, RandomFunction.random(maxHit))
        Projectile.magic(familiar, target, 1497, 80, 36, 70, 10).send()
        familiar.visualize(Animation.create(8026), Graphic.create(1496))
        if (!(familiar is BronzeMinotaurNPC || familiar is RuneMinotaurNPC) && RandomFunction.random(10) < 6) {
            val ticks = 2 + floor(familiar.location.getDistance(target.location) * 0.5) as Int
            Pulser.submit(object : Pulse(ticks) {
                override fun pulse(): Boolean {
                    stun(target, 4)
                    return true
                }
            })
        }
        return true
    }

    /**
     * Bronze minotaur npc.
     */
    inner class BronzeMinotaurNPC
    /**
     * Instantiates a new Bronze minotaur npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    @JvmOverloads constructor(owner: Player? = null, id: Int = 6853) :
        Familiar(owner, id, 3000, 12073, 6, WeaponInterface.STYLE_DEFENSIVE) {
        override fun construct(owner: Player, id: Int): Familiar {
            return BronzeMinotaurNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return bullRush(this, special, 4)
        }

        override fun isPoisonImmune(): Boolean {
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(6853, 6854)
        }
    }

    /**
     * Iron minotaur npc.
     */
    inner class IronMinotaurNPC
    /**
     * Instantiates a new Iron minotaur npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    @JvmOverloads constructor(owner: Player? = null, id: Int = 6855) :
        Familiar(owner, id, 3700, 12075, 6, WeaponInterface.STYLE_DEFENSIVE) {
        override fun construct(owner: Player, id: Int): Familiar {
            return IronMinotaurNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return bullRush(this, special, 6)
        }

        override fun isPoisonImmune(): Boolean {
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(6855, 6856)
        }
    }

    /**
     * Steel minotaur npc.
     */
    inner class SteelMinotaurNPC
    /**
     * Instantiates a new Steel minotaur npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    @JvmOverloads constructor(owner: Player? = null, id: Int = 6857) :
        Familiar(owner, id, 4600, 12077, 6, WeaponInterface.STYLE_DEFENSIVE) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SteelMinotaurNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return bullRush(this, special, 9)
        }

        override fun getCombatStyle(): CombatStyle {
            return CombatStyle.MELEE
        }

        override fun isPoisonImmune(): Boolean {
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(6857, 6858)
        }
    }

    /**
     * Mithril minotaur npc.
     */
    inner class MithrilMinotaurNPC
    /**
     * Instantiates a new Mithril minotaur npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    @JvmOverloads constructor(owner: Player? = null, id: Int = 6859) :
        Familiar(owner, id, 5500, 12079, 6, WeaponInterface.STYLE_DEFENSIVE) {
        override fun construct(owner: Player, id: Int): Familiar {
            return MithrilMinotaurNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return bullRush(this, special, 13)
        }

        override fun isPoisonImmune(): Boolean {
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(6859, 6860)
        }
    }

    /**
     * Adamant minotaur npc.
     */
    inner class AdamantMinotaurNPC
    /**
     * Instantiates a new Adamant minotaur npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    @JvmOverloads constructor(owner: Player? = null, id: Int = 6861) :
        Familiar(owner, id, 6600, 12081, 6, WeaponInterface.STYLE_DEFENSIVE) {
        override fun construct(owner: Player, id: Int): Familiar {
            return AdamantMinotaurNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return bullRush(this, special, 16)
        }

        override fun getCombatStyle(): CombatStyle {
            return CombatStyle.MELEE
        }

        override fun isPoisonImmune(): Boolean {
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(6861, 6862)
        }
    }

    /**
     * Rune minotaur npc.
     */
    inner class RuneMinotaurNPC
    /**
     * Instantiates a new Rune minotaur npc.
     *
     * @param owner the owner
     * @param id    the id
     */
    @JvmOverloads constructor(owner: Player? = null, id: Int = 6863) :
        Familiar(owner, id, 15100, 12083, 6, WeaponInterface.STYLE_DEFENSIVE) {
        override fun construct(owner: Player, id: Int): Familiar {
            return RuneMinotaurNPC(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return bullRush(this, special, 20)
        }

        override fun isPoisonImmune(): Boolean {
            return true
        }

        override fun getIds(): IntArray {
            return intArrayOf(6863, 6864)
        }
    }
}
