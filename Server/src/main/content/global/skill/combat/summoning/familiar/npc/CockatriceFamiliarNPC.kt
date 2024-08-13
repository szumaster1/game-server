package content.global.skill.combat.summoning.familiar.npc

import content.global.skill.combat.summoning.familiar.Familiar
import content.global.skill.combat.summoning.familiar.FamiliarSpecial
import content.global.skill.combat.summoning.familiar.Forager
import core.game.node.entity.combat.equipment.WeaponInterface
import core.game.node.entity.impl.Projectile
import core.game.node.entity.player.Player
import core.game.node.entity.skill.Skills
import core.game.node.item.Item
import core.game.system.task.Pulse
import core.game.world.GameWorld.Pulser
import core.game.world.update.flag.context.Animation
import core.game.world.update.flag.context.Graphic
import core.plugin.ClassScanner.definePlugin
import core.plugin.Initializable
import core.plugin.Plugin

/**
 * Cockatrice familiar NPC.
 */
@Initializable
class CockatriceFamiliarNPC : Plugin<Any> {

    override fun newInstance(arg: Any?): Plugin<Any> {
        definePlugin(SpiritCockatrice())
        definePlugin(SpiritGuthatrice())
        definePlugin(SpiritZamatrice())
        definePlugin(SpiritPengatrice())
        definePlugin(SpiritCoraxatrice())
        definePlugin(SpiritVulatrice())
        definePlugin(SpiritSaratrice())
        return this
    }

    override fun fireEvent(identifier: String, vararg args: Any): Any? {
        return null
    }

    /**
     * Petrifying gaze
     *
     * @param familiar
     * @param special
     * @param skill
     * @return
     */
    fun petrifyingGaze(familiar: Familiar, special: FamiliarSpecial, skill: Int): Boolean {
        val target = special.target
        if (!familiar.canCombatSpecial(target)) {
            return false
        }
        familiar.faceTemporary(target, 2)
        familiar.visualize(Animation.create(7762), Graphic.create(1467))
        Pulser.submit(object : Pulse(1, familiar.owner, familiar, target) {
            override fun pulse(): Boolean {
                target.getSkills().updateLevel(skill, -3, 0)
                Projectile.magic(familiar, target, 1468, 40, 36, 71, 10).send()
                familiar.sendFamiliarHit(target, 10, Graphic.create(1469))
                return true
            }
        })
        return true
    }

    /**
     * Spirit cockatrice.
     */
    inner class SpiritCockatrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6875) :
        Forager(owner, id, 3600, 12095, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritCockatrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.DEFENCE)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6875, 6876)
        }
    }

    /**
     * Spirit guthatrice.
     */
    inner class SpiritGuthatrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6877) :
        Forager(owner, id, 3600, 12097, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritGuthatrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.ATTACK)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6877, 6878)
        }
    }

    /**
     * Spirit zamatrice.
     */
    inner class SpiritZamatrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6881) :
        Forager(owner, id, 3600, 12101, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritZamatrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.STRENGTH)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6881, 6882)
        }
    }

    /**
     * Spirit pengatrice.
     */
    inner class SpiritPengatrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6883) :
        Forager(owner, id, 3600, 12103, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritPengatrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.MAGIC)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6883, 6884)
        }
    }

    /**
     * Spirit coraxatrice.
     */
    inner class SpiritCoraxatrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6885) :
        Forager(owner, id, 3600, 12105, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritCoraxatrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.SUMMONING)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6885, 6886)
        }
    }

    /**
     * Spirit vulatrice.
     */
    inner class SpiritVulatrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6887) :
        Forager(owner, id, 3600, 12107, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritVulatrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.RANGE)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6887, 6888)
        }
    }

    /**
     * Spirit saratrice.
     */
    inner class SpiritSaratrice @JvmOverloads constructor(owner: Player? = null, id: Int = 6879) :
        Forager(owner, id, 3600, 12099, 3, WeaponInterface.STYLE_CAST, COCKATRICE_EGG) {
        override fun construct(owner: Player, id: Int): Familiar {
            return SpiritSaratrice(owner, id)
        }

        override fun specialMove(special: FamiliarSpecial): Boolean {
            return petrifyingGaze(this, special, Skills.PRAYER)
        }

        override fun getIds(): IntArray {
            return intArrayOf(6879, 6880)
        }
    }

    companion object {
        private val COCKATRICE_EGG = Item(12109)
    }
}
